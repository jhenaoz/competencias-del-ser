#!/bin/bash
curl -XDELETE "http://localhost:9200/person"
curl -XDELETE "http://localhost:9200/aptitude"
curl -XDELETE "http://localhost:9200/survey"

curl -XPUT "http://localhost:9200/aptitude" -d'
{
	"mappings": {
		"aptitude": {
			"dynamic": "strict",
			"properties": {
				"id": {
					"type": "long"
				},
				"es": {
					"type": "text"
				},
				"en": {
					"type": "text"
				},
				"behaviors": {
					"properties": {
						"id": {
							"type": "long"
						},
						"es": {
							"type": "text"
						},
						"en": {
							"type": "text"
						}
					}
				}
			}
		}
	}
}'

curl -XPUT "http://localhost:9200/person" -d'
{
	"mappings": {
		"employee": {
			"dynamic": "strict",
			"properties": {
				"id": {
					"type": "string"
				},
				"name": {
					"type": "string",
					"index" : "not_analyzed"
				}
			}
		}
	}
}'

curl -XPUT "http://localhost:9200/survey" -d'
{
    "mappings": {
        "survey": {
          "dynamic": "strict",
            "properties": {
                "evaluator": {
                    "type": "string",
                    "index" : "not_analyzed"
                },
                "evaluated": {
                    "type": "string",
                    "index" : "not_analyzed"
                },
                "role": {
                    "type": "string",
                    "index" : "not_analyzed"
                },
                "timestamp": {
                    "type": "date",
                    "format": "yyyy-MM-dd"
                },
                "aptitudes": {
                    "properties": {
                        "aptitude": {
                            "type": "object",
                            "properties": {
                                "id": {
                                    "type": "string"
                                },
                                "es": {
                                    "type": "string"
                                },
                                "en": {
                                    "type": "string"
                                }
                            }
                        },
                        "observation": {
                            "type": "string"
                        },
                        "behaviors": {
                            "properties": {
                                "behavior": {
                                    "type": "object",
                                    "properties": {
                                        "id": {
                                            "type": "string"
                                        },
                                        "es": {
                                            "type": "string"
                                        },
                                        "en": {
                                            "type": "string"
                                        }
                                    }
                                },
                                "score": {
                                    "type": "integer"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}'

curl -XPOST "http://localhost:9200/person/employee/_bulk?pretty" -d'
{"index":{}}
{"id": "1", "name": "Adrian Jimenez" }
{"index":{}}
{"id": "2", "name": "Fernando Castilla" }
{"index":{}}
{"id": "3", "name": "Josias Montoya" }
{"index":{}}
{"id": "4", "name": "Juan Henao" }
{"index":{}}
{"id": "5", "name": "Julian Vanegas" }
{"index":{}}
{"id": "6", "name": "Santiago Alvear" }
{"index":{}}
{"id": "7", "name": "Sebastian Zapata" }
'

curl -XPOST "http://localhost:9200/aptitude/aptitude/_bulk?pretty" -d'
{"index":{"_id": "1"}}
{"id":1, "es": "Apertura","en": "Openness", "behaviors": [{"id": "1", "es": "Acepta sugerencias sin distinción de quien las de", "en": "They accept suggestions regardless of who gives them"}, {"id": "2", "es": "Reconoce sus errores", "en": "They recognize their mistakes"}, {"id": "3", "es": "Solicita sugerencias", "en": "They ask for suggestions"}, {"id": "4", "es": "Se evidencian sus acciones de mejora", "en": "Their actions towards improvement are apparent"}, {"id": "5", "es": "Informa oportunamente situaciones que representan riesgos u obstáculos para el equipo", "en": "They opportunely inform of situations that represent risks and obstacles for the team"}]}
{"index":{"_id": "2"}}
{"id":2, "es": "Comunicación","en": "Communication", "behaviors": [{"id": "1", "es": "Comunica sus ideas de manera clara, garantizando que las otras personas entiendan el mensaje", "en": "They communicate their ideas clearly, ensuring that other people understand the message"}, {"id": "2", "es": "Al transmitir sus ideas lo hace en buenos términos y con un tono de voz adecuado", "en": "When transmitting their ideas they do it in good terms and with an appropriate voice tone"}, {"id": "3", "es": "Escucha a los demás permitiendo que expresen de forma completa sus ideas y acepta como válidas opiniones diferentes", "en": "They listen to those around, allowing people to express their ideas in a complete manner, and accept different opinions as valid"}, {"id": "4", "es": "Basa su comunicación en ejemplos y situaciones evitando suponer", "en": "They base their communication on examples and situations, avoiding making assumptions"}, {"id": "5", "es": "Antes de entablar la comunicación entiende cómo se siente o está la otra persona, para saber si es el momento adecuado", "en": "Before establishing communication they try to understand how the other feels, seeking to ensure that the moment is appropriate"}]}
{"index":{"_id": "3"}}
{"id":3, "es": "Iniciativa","en": "Initiative", "behaviors": [{"id": "1", "es": "Busca y propone alternativas para solucionar alguna situación determinada (opciones de mejora, obstáculos) sin esperar que alguien se lo diga", "en": "They search and propose alternatives to solve particular situations (improvement options, obstacles) without someone telling them to do so"}, {"id": "2", "es": "Propone alternativas que tienen en cuenta la relación costo / beneficio (analizando que tan costoso es algo frente al beneficio obtenido)", "en": "They propose alternatives that consider the cost-benefit (analyzing the cost compared with the benefit received)"}, {"id": "3", "es": "Las acciones propuestas están alineadas con la situación que se requiere solucionar", "en": "Their suggested actions are aligned with the situation and the solution sought"}, {"id": "4", "es": "Define acciones concretas para llevar a cabo la implementación de las propuestas realizadas", "en": "They define concrete actions to execute the implementation of the proposals"}]}
{"index":{"_id": "4"}}
{"id":4, "es": "Orientación al cliente","en": "Client orientation", "behaviors": [{"id": "1", "es": "Identifica las necesidades de los clientes", "en": "They identify the client needs correctly"}, {"id": "2", "es": "Muestra preocupación y sensibilidad por solucionar los problemas del cliente", "en": "They show concern and sensitivity towards solving the client\u0027s problems"}, {"id": "3", "es": "Define acciones concretas que permiten solucionar las necesidades o problemas del cliente", "en": "They define concrete actions that allow to solve the client\u0027s problems and needs"}, {"id": "4", "es": "Propone soluciones que sorprenden al cliente entregando más de lo que esperaba", "en": "They propose solutions that surprise the client, delivering more than expected"}, {"id": "5", "es": "Genera relaciones con el cliente basadas en la confianza, cumpliendo los compromisos definidos y diciendo siempre la verdad", "en": "They create connections with the client based on trust, fulfilling the defined agreement and always telling the truth"}]}
{"index":{"_id": "5"}}
{"id":5, "es": "Logros y resultados","en": "Achievements and results", "behaviors": [{"id": "1", "es": "Cumple con los compromisos adquiridos", "en": "They fulfill their commitments"}, {"id": "2", "es": "Identifica y consigue los elementos requeridos para el cumplimiento de los compromisos", "en": "They identify and obtain the required elements for the fulfillment of their commitments"}, {"id": "3", "es": "Constantemente esta en búsqueda de nuevos retos y aprendizajes", "en": "They are constantly searching for new challenges and learning opportunities"}, {"id": "4", "es": "Se anticipa a posibles obstáculos y define acciones de mitigación necesarias", "en": "They anticipate possible obstacles and define necessary mitigation actions"}]}
{"index":{"_id": "6"}}
{"id":6, "es": "Trabajo en equipo","en": "Teamwork", "behaviors": [{"id": "1", "es": "Colabora y coopera con las personas de su equipo aun en tareas por encima de sus responsabilidades", "en": "They collaborate and cooperate with their teammates even in tasks beyond their responsibility"}, {"id": "2", "es": "Comparte sus ideas y conocimientos con otros", "en": "They share their ideas and knowledge with others"}, {"id": "3", "es": "Se le facilita reconocer el logro de otros", "en": "It\u0027s easy for them to recognize other\u0027s achievements"}, {"id": "4", "es": "Se preocupa por solucionar situaciones que puedan generar malos entendidos dentro del equipo", "en": "They are concerned about solving situations that could cause misunderstanding within the team"}]}
{"index":{"_id": "7"}}
{"id":7, "es": "Liderazgo para el desarrollo","en": "Development oriented leadership", "behaviors": [{"id": "1", "es": "Ayuda a otros a mejorar su desempeño, resolver sus problemas y ampliar sus conocimientos", "en": "They help others towards improving their performance, solving their problems and increasing their knowledge"}, {"id": "2", "es": "Brinda feedback a las personas de manera oportuna cuando descubre que hay un aspecto por mejorar", "en": "They provide feedback oportunately to other people when they discover aspects to be improved upon"}, {"id": "3", "es": "Busca que las demás personas le den feedback a sus compañeros", "en": "They seek that other people provide feedback to their colleagues"}, {"id": "4", "es": "Promueve espacios que permitan el mejoramiento continuo y feedback a las personas del equipo", "en": "They promote programs that provide constant improvement and feedback to the team members"}]}
{"index":{"_id": "8"}}
{"id":8, "es": "Liderazgo para el logro","en": "Achievement oriented leadership", "behaviors": [{"id": "1", "es": "Comunica claramente los objetivos del equipo y los requisitos para alcanzarlos", "en": "They communicate clearly the team\u0027s objectives and requirements to reaching them"}, {"id": "2", "es": "Obtiene los recursos y gestiona lo requerido para el logro de los objetivos", "en": "They obtain the resources and manage the requirements needed to reach the objectives"}, {"id": "3", "es": "Pregunta y entiende los puntos de vista de todas las personas del equipo acerca de los objetivos, requisitos y actividades ", "en": "They request and understand every team member\u0027s point of view about the objectives, requirements and agenda"}, {"id": "4", "es": "Reconoce que el equipo es quien toma las decisiones y respalda las mismas", "en": "They recognize that the team is the one that makes the decisions and support those decisions"}]}
'
