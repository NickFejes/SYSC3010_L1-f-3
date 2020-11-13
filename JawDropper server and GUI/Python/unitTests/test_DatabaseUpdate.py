import unittest
from DatabaseUpdate import read_data_thingspeak

class TestDatabaseUpdate(unittest.TestCase):
    def test_read_data_thingspeak(self):
        #Test when getting data from field 1
        self.assertEqual(len(read_data_thingspeak(1159996,1,1,"P6SIQPN78X4Q2RRT")),1) 
       
        #Test when getting data from field 2
        self.assertEqual(len(read_data_thingspeak(1159996,3,1,"P6SIQPN78X4Q2RRT")),1)

         #Test when getting data from field 3
        self.assertNotEqual(len(read_data_thingspeak(1159996,2,10,"P6SIQPN78X4Q2RRT")),10)

if __name__ == '__main__': 
    unittest.main() 