# VenadosTest
PruebaAndroid

Framework usados 
ButerkNife para mostrar los elementos de la vista
Picasso para el manejo de las imagenes web

La App cuenta con Una actividad Main Activity, la cual Hace llamada al webservice:

https://venados.dacodes.mx/api/games
Se almacenan en una dos listas, haciendo referencia a Liga MX y a liga de asenso
Cuenta con un Collpasing Toolbar

Se mandan a llamar los Fragmentos HomeFragment y GalleryFragment
Estos reciben su respectiva Lista mediante los argumentos del Bundle al ser llamdos los fragmetos
y los muestran en un RecyclerView (Los Fragmentos se mandan a llamar con un Tab)


El MainActivity cuenta con un Menu Drawer con un NavController el cual desde cualquier fragmento, puede
mandar a cualquier instancia de este NacControler

DrawerLayout NavController tiene las siguientes instancias:
Home (Muestra a HomeFragment / GalleryFragment)
stadistics (Muestra a SendFragment)
Players (Muestra a SlideshowFragment)

Stadistics manda a llamar al webservice:
https://venados.dacodes.mx/api/statistics



Players manda a llamar al WebService:
https://venados.dacodes.mx/api/players

Muestra los jugadores en un GridView

Contiene un Poup Windows que muestra el detalle de los jugadores.

