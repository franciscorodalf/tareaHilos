package ies.puerto;

class CazaHorrocruxes {
    atomic boolean encontrado = false
    atomic String ganador = null

    runnable Buscador(nombre, ubicacion):
        tiempo = RandomInt(500, 2000)
        Sleep(tiempo)
        if not encontrado:
            encontrado = true
            ganador = nombre
            print(nombre + " encontró un Horrocrux en " + ubicacion + ". ¡Búsqueda terminada!")

    procedure main():
        t1 = Thread.start(Buscador("Harry", "Bosque Prohibido"))
        t2 = Thread.start(Buscador("Hermione", "Biblioteca Antigua"))
        t3 = Thread.start(Buscador("Ron", "Mazmorras del castillo"))
        t1.join(); t2.join(); t3.join()
}