# PROYECTO FINAL DE POO: FARMACIA ESTELAR 
Integrantes: <br>
Melani Barrera <br>
Brandon Sandoval <br>
Andrew Vilcacundo <br>
Washington Villares <br>

En este informe vamos a detallar paso a paso de como fuimos realizando este proyecto <br>
1.Creacion de la base de datos y tablas a utilizar <br>
Tabla Facturas: Esta va a contener todos los datos necesarios que se necesita para la factura <br>
Tabla Productos: Aqui van a estar registrados todos los productos que se encuentran en stock <br>
Tabla usuarios: aqui van a estar registrados todos los usuarios que deseen estar afiliados a nuestra farmacia <br>
Tabla useradmin : Aqui van a estar todos los administradores de la farmacia <br>

![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/bdd3a1da-98e3-48ee-91a2-e50edc5b31c9)

2.Realizacion de los form necesarios <br>
EL login en el cual los administradores, van a tener que ingresar sus credenciales para poder ingresar al sistema <br>
![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/bc4ab074-bfa7-4fe0-b71c-f4f31eafa9e5)

Como administradores tenemos diferentes funcionalidades como: agregar productos, usuarios y revisar las ventas
![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/a61e2131-7177-41fb-8f72-1b1035b5cf03)

Aqui tenemos el registro de productos el cual consta de un CRUD para la insercion de nuevos productos
![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/858815c9-67f9-4c5a-8930-7ba874ab616c)

Aqui tenemos el resgitro de usuarios en el cual podremos agregar y mostrar todos los usuarios que esten afiliados a nuestra farmacia <br>
![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/1dda0b74-8bc9-4eba-9836-7ab6fe2b29d1)

Y como ultima funcionalidad tenemos la de revisar las facturas, en la cual podemos revisar las facturas vendidas y en tal caso volverlas a imprimir
<br>

![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/3e4b5f71-bab3-439d-80cb-b7feeb52d24c)


Ahora nos vamos a las funciones del cajero 
El login del cajero, este no va a tener credenciales ya que estos solo tendran pocas funcionalidades como la de vender los productos y generar una factura para la venta <br>
![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/06f1ef86-22db-4616-8b9d-05f08988829e)

El cajero tendra como una "Tienda virtual" la cual nos muestra los productos en stock y cuanta con diferentes botones para una mayor facilidad al momento de realizar las ventas como: buscar el producto por el nombre  <br>

![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/f952ed13-e250-441c-9a76-aa7bda667463)

Cuanta con diferentes botones como el de calcular precio este por siacaso ya que hay clientes que solo buscan averiguar el precio para volver en otra ocacion, tambien el boton seguir comprando ya que aveces los clientes no a quieren una sola cosa es como un agregar al carrito y por ultimo el boton pagar como su nombre lo dice este nos llevara a crear una factura <br>

![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/e77fd469-8cc9-4805-a911-ba21fd1202ef)
<br>
Aqui debemos llenar los datos del cliente para realizarle su factura
<br>

3.Creacion del codigo <br>

3.1. Conectar nuestra base de datos a nuestro codigo, esto lo logramos mediante una conexcion jdbc con las siguientes lineas de codigo

![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/3e71b016-190d-476d-80b3-5095eb08c12e)

3.2.Verificacion de crecendiales en nuestro login <br>

![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/fcfae006-f03e-4cfc-a71c-7bbcadff31b8)

3.3.Creacion de nuestras tablas para una mejor presentacion de nuestros registros <br>

![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/b7516ce3-f891-4612-aa7c-4cd8cfe9845d)


3.4.Esta parte de nuestro codigo sirve para seleccionar los productos y usuarios me explico damos click sobre algun regsitro y estos se ven reflejados en nuestros JTextField <br>

![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/9372e118-5114-4b81-814c-acbbfcd7db2f)

3.5.Y aqui tenemos el codigo para generar la factura con todos lo atributos que necesitamos. <br>

![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/0b9773bb-cc8f-495d-a3ac-e2111860a03d)

![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/bfd946be-1d32-4c8a-8766-f47f9739faab)


![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/702aae65-388c-4b27-bf51-276cfb9abc56)


![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/51e9648f-98fe-470d-9c39-c24731be6145)


4.Las librerias utilizadas en este proyecto han sido. <br>

![imagen](https://github.com/SandovalBrandon1027/PROYECTO__FARMACIA/assets/117743657/51bd9837-9605-43a8-a88d-136c4b199b46)

La libreria itextpdf, nos sirve para generar la factura. <br>
La libreria mysql-connector, nos sirve para conectar nuestras bases de datos a nuestro proyecto y asi poder maniparlos mediante codigo.

Vídeo realizado sobre el manual de usuario:

https://youtu.be/8zrrnv0OVN0


Muchas gracias por su atención






