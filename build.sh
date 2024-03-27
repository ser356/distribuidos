#!/bin/bash

# Comprueba si se proporcionó un nombre para el entorno virtual
if [ -z "$1" ]
then
    echo "Por favor, proporciona un nombre para el entorno virtual."
    exit 1
fi
pip install virtualenv
# Ruta a Python 2.7
PYTHON_PATH="/usr/bin/python2.7"

# Crea el entorno virtual
python -m virtualenv -p $PYTHON_PATH $1

# Comprueba si el entorno virtual se creó con éxito
if [ $? -eq 0 ]
then
    echo "Entorno virtual creado con éxito."
    source $1/bin/activate
    wait
    echo "Instala los requisitos..."
    pip install -r requirements.txt
    echo "Sirviendo app"
    python app.py
else
    echo "Hubo un error al crear el entorno virtual."
    exit
fi
