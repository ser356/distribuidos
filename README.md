# NTP RESTful

```console
source build.sh <nombre del entorno>
```

Esto creará un entorno virtual con el nombre que se le pase como argumento y lo activará.

Además está preparado para instalar los requisitos y levantar el servidor en `nogal.usal.es`.

Los clientes que quieran consumir la API , deberan activar el entorno previamente creado con `source <nombre del entorno>/bin/activate` y ejecutar 
`python cliente.py` para enviar peticiones.

NOTA: El runtime es el aceptado por `nogal.usal.es` ~ py.2.7



![alt text](assets/image.png)
