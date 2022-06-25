# nba-stats-analyzer

## Guia de instalacion

### Librerias utilizadas

- Anyadir libreria GSON [Descargar online](https://jar-download.com/artifacts/com.google.code.gson/gson/2.8.2/source-code)
- Anaydir libreria jade [Descargar online](https://moodle.upm.es/titulaciones/oficiales/mod/resource/view.php?id=409687)

### Configuracion de agentes

- *main class*: jade.Boot
- *Program arguments*: -gui DataAgent:agents.DataAgent;ExecutorAgent:agents.ExecutorAgent;VisualizationAgent:agents.VisualizationAgent

## Breve descripcion

Nba Stats Analyzer es la herramienta utilizada por los general managers de los equipos integrantes de la NBA. Esta herramienta
permite editar el salario de los jugadores y visuzlizar los datos mas relevantes de cada jugador

Existen validadores para garantizar que el nombre del jugador pertenece a la lista de jugadores registrados y a su vez que
el salario entra dentro de los limites establecidos