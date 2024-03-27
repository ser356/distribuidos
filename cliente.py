#-*- coding: utf-8 -*-
# implementation of a client to consume RESTful NTP API
import requests
import time
def determine_offset(t0, t1, t2, t3):
    """Calculate the offset of a server"""
    return ((t1 - t0) + (t2 - t3)) / 2

def determine_delay(t0, t1, t2, t3):
    """Calculate the delay of a server"""
    return (t3 - t0) - (t2 - t1)

def query_server(server_url):
    """Query a server and calculate the best offset and delay"""
    best_pair = {'d': float('inf'), 'o': float('inf')}
    for _ in range(8):  # Perform 8 repetitions
        t0 = time.time()
        response = requests.get(server_url + '/askTime')
        t3 = time.time()
        t1, t2 = response.json()['t1'], response.json()['t2']
        o = determine_offset(t0, t1, t2, t3)
        d = determine_delay(t0, t1, t2, t3)
        if d < best_pair['d']:
            best_pair = {'d': d, 'o': o}
    return best_pair

if __name__ == '__main__':
   	print(query_server("http://nogal.usal.es:5000")) 
