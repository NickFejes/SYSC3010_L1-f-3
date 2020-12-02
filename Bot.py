from discord import Webhook, RequestsWebhookAdapter
webhook = Webhook.partial(769964218719273100, 'u7q9hAi_aY9j-4JpCP1ZVMKjYfj4eejeeh8UH9bXutj105Vk0Tk4QwTpPlAG8tpw5FiO', adapter=RequestsWebhookAdapter())


def alert(machine):
    webhook.send(f'Dispenser {machine} is out of candy and needs to be refilled', username='ALERT')


#alert('b')
#print("DONE")
