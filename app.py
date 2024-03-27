#-*- coding: utf-8 -*-
# Autores
# Sergio Garcia Seco
# Andres Blazquez Colino
# app.py define el servicio NTP 
# Importación de librerías necesarias (ver requirements.txt)
from flask import Flask, jsonify, request
import time
import random

# Proceso servidor que simula NTP
app = Flask(__name__)

@app.route('/askTime', methods=['GET'])
# job que simula el tiempo de respuesta de un servidor NTP

def ask_time():
    t1 = time.time()
    time.sleep(random.uniform(0.1, 0.5))  # Simulación de tiempo de respuesta
    t2 = time.time()
    return jsonify({'t1': t1, 't2': t2})

def run_server(port,hostname):
    app.run(debug=False,host=hostname, port=port,threaded=True)
    
   
if __name__ == '__main__':
       run_server(5000, 'nogal.usal.es')
