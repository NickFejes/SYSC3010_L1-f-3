import urllib.request
import requests
import threading
import json
import mysql.connector
import time

def update_timeLog_table():
    mydb = mysql.connector.connect(host="localhost", user="root",passwd="moonis221199",database='CandyDispenser')
    myquery = mydb.cursor(buffered=True)

    channelIds = []
    readKeys = []
    myquery.execute("select TsChannelId, ReadKey  from machines ;")
    for i in myquery:
        channelIds.append(str(i[0]))
        readKeys.append(str(i[1]))

        myquery.execute("SET SQL_SAFE_UPDATES = 0")
        myquery.execute("DELETE FROM timeLog")
        mydb.commit()

    for channelId,readKey in zip(channelIds, readKeys):
         t = read_data_thingspeak(channelId,2,1000,readKey)
         for i in  t:
             chars =list(i)
             year = chars[0] + chars[1]
             month =chars[2] + chars[3]
             day =chars[4] + chars[5]
             hour =chars[6] + chars[7]
             minute =chars[8] + chars[9]
             seconds =chars[10] + chars[11]
             dateTime="20"+year+"-"+month+"-"+day+" "+hour+":"+ minute+":"+seconds
             Query="INSERT INTO timeLog values("+channelId+",'"+str(dateTime)+"')"
             myquery.execute(Query)
             mydb.commit()

def update_machines_table():

    mydb = mysql.connector.connect(host="localhost", user="root",passwd="moonis221199",database='CandyDispenser')
    myquery = mydb.cursor(buffered=True)

    channelIds = []
    readKeys = []
    myquery.execute("select TsChannelId, ReadKey  from machines ;")
    for i in myquery:
        channelIds.append(str(i[0]))
        readKeys.append(str(i[1]))
    

    for channelId,readKey in zip(channelIds, readKeys):
        
        t = read_data_thingspeak(channelId,1,1,readKey)
        Query ="update machines set TotalDispensed ="+str(t[0])+" where TsChannelId="+channelId
        myquery.execute(Query)
        mydb.commit()

        t = read_data_thingspeak(channelId,3,1,readKey)
        Query ="update machines set CurrentAmount ="+str(t[0])+" where TsChannelId="+channelId
        myquery.execute(Query)
        mydb.commit()
    
    print("Tables updated")


def read_data_thingspeak(channelId,field,amount,readKey):
    URL='https://api.thingspeak.com/channels/'+str(channelId)+'/fields/'+str(field) +'.json?api_key='
    KEY= str(readKey)
    HEADER='&results='+ str(amount)
    NEW_URL=URL+KEY+HEADER

    get_data=requests.get(NEW_URL).json()
    
    feild_1=get_data['feeds']
    print(get_data['channel']['id'])

    t=[]
    for x in feild_1:
        t.append(x['field'+str(field)])

    return t

def update():
    update_machines_table()
    update_timeLog_table()

#while True:
    #time.sleep(5)
    #update()


update()