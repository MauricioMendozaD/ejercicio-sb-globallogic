# ejercicio-sb-globallogic

Ejercicio que disponibiliza una API Rest con SpringBoot.

## Instalación

Ejecutar el build de gradle en la raiz del proyecto para generar los jars correspondientes.

```bash
./graddle build
```

## Uso
Se debe consumir la API REST expuesta como POST:
```uri
http://localhost:8080/users
```

 e ingresar la siguiente estructura json
```json
{
  "name": "Juan Rodriguez",
  "email": "asdf@asdf1.com",
  "password": "Hunter22",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "countrycode": "57"
    }
  ]
}
```
Cabe destacar las siguientes restricciones para los elementos:

* email: Debe tener el formato correcto de un email.
* password: Debe comenzar con una mayúscula continuar con letras minúsculas y terminar con 2 números.

## Diseño
Dentro de la carpeta "Documentación" se encuentra un diagrama de secuencia y un diagrama de componentes que explican el proceso de registro del usuario y la generación del token.

## Autor
Mauricio Mendoza. \
Ingeniero Civil en Informática \
Universidad Católica de Temuco
