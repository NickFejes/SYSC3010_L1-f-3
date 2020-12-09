import urllib.request
import requests
import threading
import json
import mysql.connector
import time

#Updates timeLog table in database from thingspeak
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
         print(channelId)
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
             print(str(dateTime))
             myquery.execute(Query)
             mydb.commit()

    print("Time log table updated")

#Updates machines table in database from thingspeak
def update_machines_table():

    mydb = mysql.connector.connect(host="localhost", user="root",passwd="moonis221199",database='CandyDispenser')
    myquery = mydb.cursor(buffered=True)

    channelIds = []
    readKeys = []
    myquery.execute("select TsChannelId, ReadKey  from machines ;")
    for i in myquery:
        channelIds.append(str(i[0]))
        print(str(i[0]))
        print("ok")
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
    
    print("Machine table updated")

#gets data from thingspesk with specified field samount and channel ID
def read_data_thingspeak(channelId,field,amount,readKey):
    URL='https://api.thingspeak.com/channels/'+str(channelId)+'/fields/'+str(field) +'.json?api_key='
    KEY= str(readKey)
    HEADER='&results='+ str(amount)
    NEW_URL=URL+KEY+HEADER
    print(NEW_URL)
    get_data=requests.get(NEW_URL).json()
    
    feild_1=get_data['feeds']

    t=[]
    for x in feild_1:
        t.append(x['field'+str(field)])

    print(str(amount) + " Data points fetched from Thingspeak field "+str(field))

    return t


# runs timelog and machine update functions
def update():
    update_machines_table()
    update_timeLog_table()

# runs update functions with 1 second delay
while True:
    time.sleep(1)
    update()


