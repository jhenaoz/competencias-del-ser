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
{"id": 4, "name": "Adolfo Valencia Jurado"}
{"index":{}}
{"id": 496, "name": "Adrián Yadir Jiménez Carrillo"}
{"index":{}}
{"id": 6, "name": "Adriana Maria Aguilar Viloria"}
{"index":{}}
{"id": 7, "name": "Adriana Maria Londono Galeano"}
{"index":{}}
{"id": 8, "name": "Alan Barbosa Mendoza"}
{"index":{}}
{"id": 497, "name": "Alberto Enrique Hernandez Sierra"}
{"index":{}}
{"id": 9, "name": "Alberto Giovanni Quintero Ortega"}
{"index":{}}
{"id": 10, "name": "Alejandra Maria Gomez Aristizabal"}
{"index":{}}
{"id": 11, "name": "Alejandra Maria Zuluaga Zapata"}
{"index":{}}
{"id": 12, "name": "Alejandro Bermudez Martinez"}
{"index":{}}
{"id": 13, "name": "Alejandro Calderon Velez"}
{"index":{}}
{"id": 483, "name": "Alejandro Franco Barrios"}
{"index":{}}
{"id": 15, "name": "Alejandro Ramirez Hernandez"}
{"index":{}}
{"id": 16, "name": "Alex Henry Giraldo Zea"}
{"index":{}}
{"id": 17, "name": "Alex William Moreno Rivas"}
{"index":{}}
{"id": 18, "name": "Alexander Botero Bustamante"}
{"index":{}}
{"id": 5, "name": "Aleyda Restrepo Cardona"}
{"index":{}}
{"id": 19, "name": "Alvaro Andres Castellanos Cervera"}
{"index":{}}
{"id": 526, "name": "Alvaro Uriel Montes Gomez"}
{"index":{}}
{"id": 527, "name": "Alveiro Garcia Nino"}
{"index":{}}
{"id": 20, "name": "Amaury Biquet"}
{"index":{}}
{"id": 21, "name": "Ana Lucia Aramburo Siegert"}
{"index":{}}
{"id": 22, "name": "Ana Lucia Castillo Ramirez"}
{"index":{}}
{"id": 23, "name": "Anamaria Ortiz Rodriguez"}
{"index":{}}
{"id": 24, "name": "Anderson Alexis Mejia Valencia"}
{"index":{}}
{"id": 26, "name": "Andrea Milena Morillo Cueto"}
{"index":{}}
{"id": 27, "name": "Andres Alberto Restrepo Herron"}
{"index":{}}
{"id": 28, "name": "Andres Camilo Amado Cardenas"}
{"index":{}}
{"id": 29, "name": "Andres Esteban Tamayo Uribe"}
{"index":{}}
{"id": 30, "name": "Andres Felipe Arrubla Zapata"}
{"index":{}}
{"id": 31, "name": "Andres Felipe De Leon Betancur"}
{"index":{}}
{"id": 493, "name": "Andres Felipe Gomez Rojas"}
{"index":{}}
{"id": 33, "name": "Andres Felipe Suarez Osorio"}
{"index":{}}
{"id": 34, "name": "Andres Fernando Arroyave Yepes"}
{"index":{}}
{"id": 36, "name": "Andres Mauricio Gonzalez Rojas"}
{"index":{}}
{"id": 37, "name": "Andres Mauricio Urrego Palacio"}
{"index":{}}
{"id": 39, "name": "Andres Sanchez Munoz"}
{"index":{}}
{"id": 40, "name": "Angela Maria Bustamante Restrepo"}
{"index":{}}
{"id": 41, "name": "Angela Maria Patarroyo Montenegro"}
{"index":{}}
{"id": 498, "name": "Angelica Maria Castaneda Villamil"}
{"index":{}}
{"id": 43, "name": "Ariel Eduardo Pena Ocando"}
{"index":{}}
{"id": 45, "name": "Astrid Riveros Pardo"}
{"index":{}}
{"id": 46, "name": "Beatriz Elena Sanchez Trochez"}
{"index":{}}
{"id": 48, "name": "Camilo Alberto Gutierrez Vargas"}
{"index":{}}
{"id": 49, "name": "Camilo Andres Varela Leon"}
{"index":{}}
{"id": 50, "name": "Camilo Andres Velez Gutierrez"}
{"index":{}}
{"id": 51, "name": "Camilo Velasquez Taborda"}
{"index":{}}
{"id": 52, "name": "Carlos Alberto Londono Perez"}
{"index":{}}
{"id": 53, "name": "Carlos Alberto Quiroz Builes"}
{"index":{}}
{"id": 54, "name": "Carlos Alfredo Suarez Berrio"}
{"index":{}}
{"id": 528, "name": "Carlos Andres Arboleda Zapata"}
{"index":{}}
{"id": 557, "name": "Carlos Andres Arboleda Zapata"}
{"index":{}}
{"id": 55, "name": "Carlos Andres Cardenas Pachon"}
{"index":{}}
{"id": 56, "name": "Carlos Andres Hernandez Eusse"}
{"index":{}}
{"id": 57, "name": "Carlos Andres Zapata Cuervo"}
{"index":{}}
{"id": 58, "name": "Carlos Eduardo Tovar Pinto"}
{"index":{}}
{"id": 59, "name": "Carlos Esteban Perez Uribe"}
{"index":{}}
{"id": 60, "name": "Carlos Guillermo Duarte Bautista"}
{"index":{}}
{"id": 61, "name": "Carlos Harim Perez Rodriguez"}
{"index":{}}
{"id": 62, "name": "Carlos Mario Bedoya Munera"}
{"index":{}}
{"id": 63, "name": "Carmen Rosa Ruidiaz Martinez"}
{"index":{}}
{"id": 64, "name": "Carolina Arboleda Gomez"}
{"index":{}}
{"id": 65, "name": "Carolina Contreras Landinez"}
{"index":{}}
{"id": 66, "name": "Carolina Correa Orozco"}
{"index":{}}
{"id": 67, "name": "Carolina Echeverri Cardona"}
{"index":{}}
{"id": 555, "name": "Cartlos Andres Arboleda Zapata"}
{"index":{}}
{"id": 556, "name": "Cartlos Andres Arboleda Zapata"}
{"index":{}}
{"id": 68, "name": "Catalina Cordoba Charria"}
{"index":{}}
{"id": 69, "name": "Catalina Gomez Alzate"}
{"index":{}}
{"id": 70, "name": "Catalina Soto Zuluaga"}
{"index":{}}
{"id": 71, "name": "Cesar Andres Vargas Parra"}
{"index":{}}
{"id": 72, "name": "Cesar Armando Villalobos Rubiano"}
{"index":{}}
{"id": 74, "name": "Cesar Augusto Gomez Bustamante"}
{"index":{}}
{"id": 75, "name": "Cesar Augusto Mosquera Palacios"}
{"index":{}}
{"id": 529, "name": "Cesar Manuel Romero Arroyo"}
{"index":{}}
{"id": 77, "name": "Claudia Elena Gaviria Bedoya"}
{"index":{}}
{"id": 499, "name": "Cristhian Giovanni Guevara Alarcon"}
{"index":{}}
{"id": 530, "name": "Cristhian Mauricio Preciado Hernandez"}
{"index":{}}
{"id": 78, "name": "Cristian Camilo Molina Mendoza"}
{"index":{}}
{"id": 79, "name": "Cristian Camilo Munoz Martinez"}
{"index":{}}
{"id": 80, "name": "Cristian Duvan Ganan Pena"}
{"index":{}}
{"id": 82, "name": "Cyndi Jeanette Carvajal Calle"}
{"index":{}}
{"id": 83, "name": "Daniel Alberto Martinez Galvis"}
{"index":{}}
{"id": 500, "name": "Daniel Correa Barrios"}
{"index":{}}
{"id": 85, "name": "Daniel Esteban Fuentes Gutierrez"}
{"index":{}}
{"id": 86, "name": "Daniel Esteban Valderrama Tangarife"}
{"index":{}}
{"id": 88, "name": "Daniel Isaza Jimenez"}
{"index":{}}
{"id": 89, "name": "Daniel Jose Ramirez Valencia"}
{"index":{}}
{"id": 90, "name": "Daniel Ricaurte Castano Jaramillo"}
{"index":{}}
{"id": 91, "name": "Daniel Stiven Restrepo Arcila"}
{"index":{}}
{"id": 486, "name": "Daniela Mancilla Valdes"}
{"index":{}}
{"id": 92, "name": "Daniela Yepes Gaviria"}
{"index":{}}
{"id": 93, "name": "Danilo Octavio Alarcon Rodriguez"}
{"index":{}}
{"id": 94, "name": "Danny Alberto Londono Henao"}
{"index":{}}
{"id": 501, "name": "Darwin Ramos Cuervo"}
{"index":{}}
{"id": 95, "name": "David Alberto Moreno Palacio"}
{"index":{}}
{"id": 96, "name": "David Alejandro Florez Restrepo"}
{"index":{}}
{"id": 97, "name": "David Alejandro Lopez Zapata"}
{"index":{}}
{"id": 98, "name": "David Alejandro Mona Palacio"}
{"index":{}}
{"id": 99, "name": "David Alejandro Morales Valencia"}
{"index":{}}
{"id": 100, "name": "David Andres Bedoya Hernandez"}
{"index":{}}
{"id": 531, "name": "David Andres Hurtado Rodriguez"}
{"index":{}}
{"id": 101, "name": "David Bohorquez Alvarez"}
{"index":{}}
{"id": 495, "name": "David Brayan Villa Reyes"}
{"index":{}}
{"id": 102, "name": "David Camilo Heredia Ardila"}
{"index":{}}
{"id": 532, "name": "David Camilo Serrano Abril"}
{"index":{}}
{"id": 103, "name": "David Camilo Torres Eraso"}
{"index":{}}
{"id": 104, "name": "David Esteban Escobar Gutierrez"}
{"index":{}}
{"id": 105, "name": "David Franco Chica"}
{"index":{}}
{"id": 106, "name": "David Julian Cardona Quintero"}
{"index":{}}
{"id": 107, "name": "David Monsalve Galeano"}
{"index":{}}
{"id": 109, "name": "David Stiven Velez Segura"}
{"index":{}}
{"id": 110, "name": "David Vasquez Rivera"}
{"index":{}}
{"id": 112, "name": "Deibys Fernando Quintero Consuegra"}
{"index":{}}
{"id": 113, "name": "Delia Pineda Mejorada"}
{"index":{}}
{"id": 114, "name": "Derly Katerine Echavarria"}
{"index":{}}
{"id": 115, "name": "Diana Alexandra Parra Perilla"}
{"index":{}}
{"id": 116, "name": "Diana Angelica Cardona Marin"}
{"index":{}}
{"id": 117, "name": "Diana Lucia Castro Ortega"}
{"index":{}}
{"id": 118, "name": "Diana Milena Davila Pedraza"}
{"index":{}}
{"id": 119, "name": "Diana Patricia Diaz Ramirez"}
{"index":{}}
{"id": 120, "name": "Didier Alberto Tabares Higuita"}
{"index":{}}
{"id": 122, "name": "Diego Alejandro Ruiz Gutierrez"}
{"index":{}}
{"id": 123, "name": "Diego Alejandro Trejos Trujillo"}
{"index":{}}
{"id": 502, "name": "Diego Alexander Hoyos David"}
{"index":{}}
{"id": 124, "name": "Diego Andres Gonzalez Galvis"}
{"index":{}}
{"id": 125, "name": "Diego Armando Bonilla Buritica"}
{"index":{}}
{"id": 126, "name": "Diego Armando Farfan Barrero"}
{"index":{}}
{"id": 130, "name": "Diego Hernan Montoya Bedoya"}
{"index":{}}
{"id": 131, "name": "Diego Leon Betancur Catano"}
{"index":{}}
{"id": 132, "name": "Diego Miguel Patino Rodriguez"}
{"index":{}}
{"id": 503, "name": "Dilson Alzate Perez"}
{"index":{}}
{"id": 133, "name": "Diomedes Igua Hernandez"}
{"index":{}}
{"id": 134, "name": "Dora Catalina Villada Arango"}
{"index":{}}
{"id": 135, "name": "Dora Luz Ocampo Goez"}
{"index":{}}
{"id": 136, "name": "Duvan Armando Moreno Marin"}
{"index":{}}
{"id": 137, "name": "Duvan Bernardo Cardenas Galeano"}
{"index":{}}
{"id": 138, "name": "Eder Marceno Cotes Solano"}
{"index":{}}
{"id": 504, "name": "Eder Navarro Martinez"}
{"index":{}}
{"id": 139, "name": "Eder Noe Bonilla Enriquez"}
{"index":{}}
{"id": 140, "name": "Edgar Alejandro Palacio Duque"}
{"index":{}}
{"id": 141, "name": "Edgar Andres Alvarez Bedoya"}
{"index":{}}
{"id": 505, "name": "Edgar Andres Marin Vega"}
{"index":{}}
{"id": 142, "name": "Edison Andres Castro Tabares"}
{"index":{}}
{"id": 143, "name": "Edna Patricia Mosquera Lopez"}
{"index":{}}
{"id": 144, "name": "Edwin Alberto Diaz Echeverri"}
{"index":{}}
{"id": 145, "name": "Edwin Alberto Posada Giraldo"}
{"index":{}}
{"id": 146, "name": "Edwin Escudero Restrepo"}
{"index":{}}
{"id": 147, "name": "Edwin Mantilla Santamaria"}
{"index":{}}
{"id": 148, "name": "Elizabeth Quintero Gonzalez"}
{"index":{}}
{"id": 149, "name": "Elsy Jhoanna Cortes Vega"}
{"index":{}}
{"id": 150, "name": "Elvira Cuartas Ospina"}
{"index":{}}
{"id": 151, "name": "Elvis Alirio Astaiza Gutierrez"}
{"index":{}}
{"id": 506, "name": "Eric Berchem Caldera"}
{"index":{}}
{"id": 152, "name": "Erika Alejandra Betancur Buritica"}
{"index":{}}
{"id": 533, "name": "Esteban Molina Abad"}
{"index":{}}
{"id": 154, "name": "Esteban Vasquez Gonzalez"}
{"index":{}}
{"id": 155, "name": "Ever Alonso Echeverri Velasquez"}
{"index":{}}
{"id": 156, "name": "Ever Alonso Torres Galeano"}
{"index":{}}
{"id": 157, "name": "Fabian Alexis Becerra Monsalve"}
{"index":{}}
{"id": 158, "name": "Fabian Esteban Herrera Herrera"}
{"index":{}}
{"id": 534, "name": "Fabio Mauricio Gallo Parra"}
{"index":{}}
{"id": 159, "name": "Fabrizio Sgura"}
{"index":{}}
{"id": 160, "name": "Faricelly Restrepo Cuartas"}
{"index":{}}
{"id": 535, "name": "Felipe Velasquez Uribe"}
{"index":{}}
{"id": 507, "name": "Fernando De Jesus Castilla Ospina"}
{"index":{}}
{"id": 162, "name": "Flor Maria Bedoya Lopez"}
{"index":{}}
{"id": 163, "name": "Francisco Javier Gaviria Sierra"}
{"index":{}}
{"id": 508, "name": "Francisco Luis Rodriguez Villabona"}
{"index":{}}
{"id": 164, "name": "Frederic Yesid Pena Sanchez"}
{"index":{}}
{"id": 165, "name": "Fredy Alexander Bedoya Soto"}
{"index":{}}
{"id": 166, "name": "Gabriel Jaime Hurtado Canola"}
{"index":{}}
{"id": 167, "name": "Gabriel Jaime Moreno Maya"}
{"index":{}}
{"id": 168, "name": "German David Potes Franco"}
{"index":{}}
{"id": 169, "name": "Gilma Yaneth Garcia Henao"}
{"index":{}}
{"id": 170, "name": "Gina Maria Arcieri Pulgarin"}
{"index":{}}
{"id": 482, "name": "Giovany Stik Tovar Garcia"}
{"index":{}}
{"id": 171, "name": "Giuseppe Fernando Caro Rodriguez"}
{"index":{}}
{"id": 172, "name": "Gladys Cecilia Giraldo Giraldo"}
{"index":{}}
{"id": 173, "name": "Gloria Maria Aguirre Sepulveda"}
{"index":{}}
{"id": 174, "name": "Gloria Patricia Velez Isaza"}
{"index":{}}
{"id": 175, "name": "Gregorio Alejandro Aristizabal Escobar"}
{"index":{}}
{"id": 176, "name": "Gustavo Adolfo Arroyave Lopez"}
{"index":{}}
{"id": 177, "name": "Gustavo Adolfo Duque Osorio"}
{"index":{}}
{"id": 178, "name": "Gustavo Adolfo Munoz Aguilar"}
{"index":{}}
{"id": 179, "name": "Gustavo Eduardo Mendoza Ramirez"}
{"index":{}}
{"id": 180, "name": "Gustavo Gonzalez Valencia"}
{"index":{}}
{"id": 509, "name": "Haiver Carrillo Castro"}
{"index":{}}
{"id": 181, "name": "Hakan Gosta Ahlstedt"}
{"index":{}}
{"id": 182, "name": "Heidy Tatiana Tamayo Fernandez"}
{"index":{}}
{"id": 510, "name": "Hernan Alfonso Cortes Navarro"}
{"index":{}}
{"id": 183, "name": "Hernan Dario Mesa Roldan"}
{"index":{}}
{"id": 184, "name": "Hernan Diaz Rodriguez"}
{"index":{}}
{"id": 536, "name": "Hernan Lopez Vergara"}
{"index":{}}
{"id": 185, "name": "Hogly Leonardo Rubio Tarazona"}
{"index":{}}
{"id": 511, "name": "Holmes Giovanny Salazar Osorio"}
{"index":{}}
{"id": 188, "name": "Ingri Johana Castaneda Cruz"}
{"index":{}}
{"id": 189, "name": "Irene Amparo Giraldo Naranjo"}
{"index":{}}
{"id": 190, "name": "Israel Alejandro Perdomo Bolanos"}
{"index":{}}
{"id": 191, "name": "Jackson Alexis Palacios Mosquera"}
{"index":{}}
{"id": 192, "name": "Jacobo Asmar Meza"}
{"index":{}}
{"id": 193, "name": "Jaiber Mauricio Suarez Robelto"}
{"index":{}}
{"id": 194, "name": "Jaime Alberto Carmona Arango"}
{"index":{}}
{"id": 489, "name": "Jaime Andres Betancur Duque"}
{"index":{}}
{"id": 195, "name": "Jairo Andres Gonzalez Franco"}
{"index":{}}
{"id": 196, "name": "Jairo Eduardo Ipial Villamil"}
{"index":{}}
{"id": 197, "name": "Jairo Leon Zapata Sepulveda"}
{"index":{}}
{"id": 198, "name": "Javier Antonio Navarro Suarez"}
{"index":{}}
{"id": 537, "name": "Javier Eduardo Jaimes Velasquez"}
{"index":{}}
{"id": 199, "name": "Javier Restrepo Rendon"}
{"index":{}}
{"id": 200, "name": "Jean Sebastian Ruiz Rojas"}
{"index":{}}
{"id": 201, "name": "Jeisson Ferney Delgado Cuentas"}
{"index":{}}
{"id": 202, "name": "Jenifer Johana Acosta Gallego"}
{"index":{}}
{"id": 203, "name": "Jenny Alejandra Florez Torres"}
{"index":{}}
{"id": 204, "name": "Jeyson Oswaldo Gallego Villegas"}
{"index":{}}
{"id": 205, "name": "Jhenny Leandra Giraldo Trujillo"}
{"index":{}}
{"id": 206, "name": "Jhon Arley Osorio Sepulveda"}
{"index":{}}
{"id": 207, "name": "Jhon Edison Henao Vega"}
{"index":{}}
{"id": 538, "name": "Jhon Edward Murillo Reyes"}
{"index":{}}
{"id": 208, "name": "Jhon Faber Echavez Posada"}
{"index":{}}
{"id": 209, "name": "Jhonatan Gonzalez Lozano"}
{"index":{}}
{"id": 210, "name": "Jhonathan Olaya Garro"}
{"index":{}}
{"id": 211, "name": "Jhonnatan Pulgarin Blandon"}
{"index":{}}
{"id": 212, "name": "Jimena Del Pilar Rincon Cruz"}
{"index":{}}
{"id": 213, "name": "Joaquin David Hernandez Cardenas"}
{"index":{}}
{"id": 214, "name": "Johan Abril Animero"}
{"index":{}}
{"id": 478, "name": "Johan Miguel Ruiz Rodriguez"}
{"index":{}}
{"id": 215, "name": "Johan Smith Gutierrez Giraldo"}
{"index":{}}
{"id": 216, "name": "Johana Sirley Rodriguez Patino"}
{"index":{}}
{"id": 217, "name": "Johann Nicolas Diaz Montero"}
{"index":{}}
{"id": 218, "name": "John Alexander Alzate"}
{"index":{}}
{"id": 219, "name": "John Alexander De La Pava Pena"}
{"index":{}}
{"id": 220, "name": "John Antonny Jaramillo Mora"}
{"index":{}}
{"id": 223, "name": "John Mauricio Ruiz Herrera"}
{"index":{}}
{"id": 224, "name": "Johnny Alexander Jaramillo Gallego"}
{"index":{}}
{"id": 225, "name": "Jonathan Alexander Acevedo Galeano"}
{"index":{}}
{"id": 226, "name": "Jonathan Hernandez Zapata"}
{"index":{}}
{"id": 227, "name": "Jorge Andres Holguin Medina"}
{"index":{}}
{"id": 228, "name": "Jorge Antonio Moreno Rodriguez"}
{"index":{}}
{"id": 229, "name": "Jorge Hernan Aramburo Siegert"}
{"index":{}}
{"id": 484, "name": "Jose Alejandro Nino Mora"}
{"index":{}}
{"id": 230, "name": "Jose Andres Oliveros Echeverry"}
{"index":{}}
{"id": 231, "name": "Jose Andres Sanchez Moreno"}
{"index":{}}
{"id": 232, "name": "Jose Cruz De Ita Reyes"}
{"index":{}}
{"id": 233, "name": "Jose Guillermo Valle Pavas"}
{"index":{}}
{"id": 234, "name": "Jose Luis Lopez Hernandez"}
{"index":{}}
{"id": 235, "name": "Jose Rafael Arrieta Dominguez"}
{"index":{}}
{"id": 512, "name": "Jose Walter Sierra Jaramillo"}
{"index":{}}
{"id": 513, "name": "Josias Daniel Montoya Campo"}
{"index":{}}
{"id": 236, "name": "Juan Camilo Arias Tamayo"}
{"index":{}}
{"id": 237, "name": "Juan Camilo Gomez Aristizabal"}
{"index":{}}
{"id": 238, "name": "Juan Camilo Morales Nino"}
{"index":{}}
{"id": 239, "name": "Juan Camilo Vallejo Restrepo"}
{"index":{}}
{"id": 240, "name": "Juan Carlos Alejandro Vasquez Aramburo"}
{"index":{}}
{"id": 242, "name": "Juan Carlos Giron Salazar"}
{"index":{}}
{"id": 243, "name": "Juan Carlos Rodriguez Lara"}
{"index":{}}
{"id": 244, "name": "Juan Carlos Torres Hernandez"}
{"index":{}}
{"id": 245, "name": "Juan Daniel Ortiz Garcia"}
{"index":{}}
{"id": 246, "name": "Juan David Cano Arboleda"}
{"index":{}}
{"id": 247, "name": "Juan David Cardona Cardona"}
{"index":{}}
{"id": 249, "name": "Juan David Gonzalez Piza"}
{"index":{}}
{"id": 250, "name": "Juan David Henao Zapata"}
{"index":{}}
{"id": 251, "name": "Juan David Munoz Velez"}
{"index":{}}
{"id": 252, "name": "Juan David Naranjo Arias"}
{"index":{}}
{"id": 253, "name": "Juan Esteban Alvarez Bustamante"}
{"index":{}}
{"id": 254, "name": "Juan Esteban Guerra Echeverry"}
{"index":{}}
{"id": 255, "name": "Juan Esteban Herrera Morales"}
{"index":{}}
{"id": 256, "name": "Juan Esteban Lopez Tamayo"}
{"index":{}}
{"id": 257, "name": "Juan Esteban Moncada Castano"}
{"index":{}}
{"id": 258, "name": "Juan Esteban Torres Solano"}
{"index":{}}
{"id": 539, "name": "Juan Felipe Builes Gallo"}
{"index":{}}
{"id": 259, "name": "Juan Felipe Ocampo Bedoya"}
{"index":{}}
{"id": 260, "name": "Juan Fernando Diaz Delgado"}
{"index":{}}
{"id": 261, "name": "Juan Fernando Londono Gomez"}
{"index":{}}
{"id": 262, "name": "Juan Fernando Moreno Marin"}
{"index":{}}
{"id": 263, "name": "Juan Fernando Valencia Toro"}
{"index":{}}
{"id": 264, "name": "Juan Fernando Velasquez Martinez"}
{"index":{}}
{"id": 265, "name": "Juan Gabriel Gomez Baron"}
{"index":{}}
{"id": 514, "name": "Juan Gabriel Romero Silva"}
{"index":{}}
{"id": 480, "name": "Juan Guillermo Gomez Montoya"}
{"index":{}}
{"id": 266, "name": "Juan Jose Uribe Lopez"}
{"index":{}}
{"id": 267, "name": "Juan Leonardo Trillos Gutierrez"}
{"index":{}}
{"id": 268, "name": "Juan Pablo Bonilla Chavez"}
{"index":{}}
{"id": 269, "name": "Juan Pablo Casas Chica"}
{"index":{}}
{"id": 270, "name": "Juan Pablo Franco Gomez"}
{"index":{}}
{"id": 271, "name": "Juan Sebastian Duenas Urrutia"}
{"index":{}}
{"id": 272, "name": "Juan Sebastian Naffah Isaza"}
{"index":{}}
{"id": 540, "name": "Juan Sebastian Perez Alvarez"}
{"index":{}}
{"id": 515, "name": "Juan Sebastian Sanchez Castillo"}
{"index":{}}
{"id": 516, "name": "Juan Sebastian Zapata Tamayo"}
{"index":{}}
{"id": 273, "name": "Juan Vicente Pena Peralta"}
{"index":{}}
{"id": 274, "name": "Julian Alberto Duque Cespedes"}
{"index":{}}
{"id": 275, "name": "Julian Andres Montoya Gaviria"}
{"index":{}}
{"id": 276, "name": "Julian Andres Soto Arcila"}
{"index":{}}
{"id": 277, "name": "Julian Eugenio Loaiza Ruiz"}
{"index":{}}
{"id": 278, "name": "Julian Stiven Garzon Acevedo"}
{"index":{}}
{"id": 517, "name": "Julian Vanegas Piedrahita"}
{"index":{}}
{"id": 279, "name": "Juliana Garcia Manrique"}
{"index":{}}
{"id": 280, "name": "Juliana Maria Cruz Echeverri"}
{"index":{}}
{"id": 281, "name": "Julie Cicely Ruiz Aguilera"}
{"index":{}}
{"id": 282, "name": "Juliet Natalia Cardenas Munoz"}
{"index":{}}
{"id": 283, "name": "Karolina Kaufman Porat"}
{"index":{}}
{"id": 541, "name": "Katerine Villamizar Suaza"}
{"index":{}}
{"id": 542, "name": "Katherine Gomez Arrieta"}
{"index":{}}
{"id": 284, "name": "Lamia Josefa Nader Ordonez"}
{"index":{}}
{"id": 285, "name": "Laura Bedoya Jaramillo"}
{"index":{}}
{"id": 286, "name": "Laura Catalina Navarro Rodriguez"}
{"index":{}}
{"id": 287, "name": "Laura Catalina Vanegas Balvin"}
{"index":{}}
{"id": 288, "name": "Laura Daniela Mejia Garcia"}
{"index":{}}
{"id": 289, "name": "Laura Lizeth Escobar Ospina"}
{"index":{}}
{"id": 290, "name": "Laura Pareja Perez"}
{"index":{}}
{"id": 291, "name": "Laura Vanessa Hernandez Benitez"}
{"index":{}}
{"id": 292, "name": "Lecxy Rocio Alzate Uribe"}
{"index":{}}
{"id": 293, "name": "Leidy Joana Alvarez Ramirez"}
{"index":{}}
{"id": 294, "name": "Leidy Viviana Castro Marin"}
{"index":{}}
{"id": 518, "name": "Leiner Fabian Orrego Giraldo"}
{"index":{}}
{"id": 295, "name": "Leonardo Andres Munoz Henao"}
{"index":{}}
{"id": 296, "name": "Leonardo Jimenez Gomez"}
{"index":{}}
{"id": 297, "name": "Lia Sanchez Echeverri"}
{"index":{}}
{"id": 298, "name": "Liliam Paola Bolanos Rengifo"}
{"index":{}}
{"id": 299, "name": "Lina Marcela Cadavid Vasquez"}
{"index":{}}
{"id": 301, "name": "Lina Marcela Monsalve Higuita"}
{"index":{}}
{"id": 543, "name": "Lina Maria Gomez Sierra"}
{"index":{}}
{"id": 303, "name": "Lina Maria Jaramillo Lopera"}
{"index":{}}
{"id": 304, "name": "Linda Vanessa Zapata Henao"}
{"index":{}}
{"id": 305, "name": "Lindelia Nieto Ramirez"}
{"index":{}}
{"id": 307, "name": "Lizeth Johana Mendoza Vergara"}
{"index":{}}
{"id": 308, "name": "Lucy Beatriz Tobias Escudero"}
{"index":{}}
{"id": 309, "name": "Luis Alberto Bolanos Salazar"}
{"index":{}}
{"id": 519, "name": "Luis Alfonso Roca Rosero"}
{"index":{}}
{"id": 311, "name": "Luis Alfredo Alcaraz Moreno"}
{"index":{}}
{"id": 544, "name": "Luis Antonio Cervantes Ortega"}
{"index":{}}
{"id": 312, "name": "Luis Carlos Torres Castro"}
{"index":{}}
{"id": 313, "name": "Luis David Carreno Henao"}
{"index":{}}
{"id": 314, "name": "Luis Eduardo Blandon Puello"}
{"index":{}}
{"id": 315, "name": "Luis Felipe Acosta Mira"}
{"index":{}}
{"id": 316, "name": "Luis Fernando Garcia Rodriguez"}
{"index":{}}
{"id": 317, "name": "Luis Fernando Hernandez Fernandez"}
{"index":{}}
{"id": 318, "name": "Luis Hernando Salas Manrique"}
{"index":{}}
{"id": 319, "name": "Luis Miguel Piedrahita Londono"}
{"index":{}}
{"id": 320, "name": "Luis Miguel Ramirez Lastra"}
{"index":{}}
{"id": 520, "name": "Luis Santiago Alvear Angel"}
{"index":{}}
{"id": 322, "name": "Luz Angela Perona Ossaba"}
{"index":{}}
{"id": 323, "name": "Luz Janeth Castano Osorio"}
{"index":{}}
{"id": 324, "name": "Luz Johanna Ariza Pardo"}
{"index":{}}
{"id": 521, "name": "Luz Marleny Arroyave Norena"}
{"index":{}}
{"id": 326, "name": "Luz Mary Gonzalez Castillo"}
{"index":{}}
{"id": 488, "name": "Luz Stella Cuervo Restrepo"}
{"index":{}}
{"id": 327, "name": "Manuel Alejandro Forero Ortegon"}
{"index":{}}
{"id": 329, "name": "Manuel Felipe Cabrales Azcarate"}
{"index":{}}
{"id": 545, "name": "Manuel Fernando Gallego Arias"}
{"index":{}}
{"id": 330, "name": "Marco Fernando Reyes Valderrama"}
{"index":{}}
{"id": 332, "name": "Marcos Enrique Manrique Pimienta"}
{"index":{}}
{"id": 333, "name": "Margarita Maria Arango Suarez"}
{"index":{}}
{"id": 335, "name": "Maria Alejandra Pulido Munoz"}
{"index":{}}
{"id": 336, "name": "Maria Alejandra Rodas Sanchez"}
{"index":{}}
{"id": 522, "name": "Maria Alejandra Torres Avellaneda"}
{"index":{}}
{"id": 337, "name": "Maria Angelica Rivas Sanabria"}
{"index":{}}
{"id": 338, "name": "Maria Catalina Torres Maturana"}
{"index":{}}
{"id": 339, "name": "Maria Clara Martinez Mendez"}
{"index":{}}
{"id": 341, "name": "Maria Cristina Arango Wiedemann"}
{"index":{}}
{"id": 342, "name": "Maria Ines Giraldo Lopez"}
{"index":{}}
{"id": 344, "name": "Maria Isabel Ruiz Henao"}
{"index":{}}
{"id": 345, "name": "Maria Luisa Fernanda Avendano Jimenez"}
{"index":{}}
{"id": 546, "name": "Maria Margarita Hoyos Cancimanse"}
{"index":{}}
{"id": 346, "name": "Maria Paula Cabra Silva"}
{"index":{}}
{"id": 348, "name": "Maria Soledad Bustamante Restrepo"}
{"index":{}}
{"id": 547, "name": "Maria Terese Ranallo"}
{"index":{}}
{"id": 349, "name": "Mariana Ramirez Atehortua"}
{"index":{}}
{"id": 350, "name": "Maribel Gomez Ramirez"}
{"index":{}}
{"id": 351, "name": "Maribel Moreno Cubides"}
{"index":{}}
{"id": 352, "name": "Mario Arango Duque"}
{"index":{}}
{"id": 353, "name": "Mario Garcia Hernandez"}
{"index":{}}
{"id": 354, "name": "Marisol Garcia Giraldo"}
{"index":{}}
{"id": 355, "name": "Marleyudemi Ruiz Torres"}
{"index":{}}
{"id": 356, "name": "Marlon Andrei Aguilar Velez"}
{"index":{}}
{"id": 357, "name": "Marlon Danilo Montano Rodriguez"}
{"index":{}}
{"id": 358, "name": "Marta Lucia Vanbruggen Benjumea"}
{"index":{}}
{"id": 359, "name": "Martha Patricia Sandoval Toro"}
{"index":{}}
{"id": 360, "name": "Mateo Delgado Patino"}
{"index":{}}
{"id": 361, "name": "Mauricio Diaz Rodriguez"}
{"index":{}}
{"id": 363, "name": "Mauricio Lopez Soto"}
{"index":{}}
{"id": 364, "name": "Mauricio Rivera Montoya"}
{"index":{}}
{"id": 548, "name": "Melina Alejandra Arevalo Arevalo"}
{"index":{}}
{"id": 365, "name": "Melissa Espinal Lopez"}
{"index":{}}
{"id": 549, "name": "Miguel Alejandro Huertas Ayala"}
{"index":{}}
{"id": 367, "name": "Miguel Angel Duarte Herrera"}
{"index":{}}
{"id": 368, "name": "Miguel Angel Rodriguez Guerra"}
{"index":{}}
{"id": 370, "name": "Miguel Eduardo Osorio Montilla"}
{"index":{}}
{"id": 371, "name": "Miguel Esteban Betin Montes"}
{"index":{}}
{"id": 373, "name": "Milton Cesar Perez Velasquez"}
{"index":{}}
{"id": 374, "name": "Monica Cristina Abreo Ariza"}
{"index":{}}
{"id": 375, "name": "Monica Gil Castro"}
{"index":{}}
{"id": 376, "name": "Monica Patricia Uribe Rozo"}
{"index":{}}
{"id": 377, "name": "Myriam Ramos Garzon"}
{"index":{}}
{"id": 378, "name": "Natalia Fernandez Posada"}
{"index":{}}
{"id": 379, "name": "Natalia Hernandez Betancur"}
{"index":{}}
{"id": 380, "name": "Natalia Rivas Restrepo"}
{"index":{}}
{"id": 381, "name": "Nestor Eduardo Fajardo Rodriguez"}
{"index":{}}
{"id": 382, "name": "Nicolas David Giraldo Yepes"}
{"index":{}}
{"id": 383, "name": "Nidian Suarez Talero"}
{"index":{}}
{"id": 384, "name": "Norman Danilo Castro Tellez"}
{"index":{}}
{"id": 385, "name": "Nury Natalia Toro Gonzalez"}
{"index":{}}
{"id": 386, "name": "Olga Beatriz Naranjo Giraldo"}
{"index":{}}
{"id": 387, "name": "Olga Lucia Ramirez Granda"}
{"index":{}}
{"id": 388, "name": "Oreida Ruiz Molina"}
{"index":{}}
{"id": 389, "name": "Orlando Villabona Bolanos"}
{"index":{}}
{"id": 391, "name": "Oscar Dario Malagon Murcia"}
{"index":{}}
{"id": 523, "name": "Oscar Eduardo Sepulveda Montoya"}
{"index":{}}
{"id": 550, "name": "Oscar Gustavo Morales Riasco"}
{"index":{}}
{"id": 524, "name": "Oscar Hernan Guevara Gil"}
{"index":{}}
{"id": 392, "name": "Oscar Ivan Lopez Pulido"}
{"index":{}}
{"id": 393, "name": "Oscar Sandoval Ramos"}
{"index":{}}
{"id": 394, "name": "Oscar Yesid Anaya Oviedo"}
{"index":{}}
{"id": 395, "name": "Oskar Adrian Leon Guavita"}
{"index":{}}
{"id": 551, "name": "Otto Ricardo Navarrette Botia"}
{"index":{}}
{"id": 396, "name": "Pablo Andres Acosta Amaya"}
{"index":{}}
{"id": 397, "name": "Pablo Andres Alzate Bedoya"}
{"index":{}}
{"id": 398, "name": "Paola Andrea Gonzalez Medina"}
{"index":{}}
{"id": 490, "name": "Pedro Jose Albor Barros"}
{"index":{}}
{"id": 401, "name": "Pedro Luis Patron Espitia"}
{"index":{}}
{"id": 402, "name": "Pedro Madrid Barbotto"}
{"index":{}}
{"id": 403, "name": "Peggy Roxana Giraldo Cordoba"}
{"index":{}}
{"id": 404, "name": "Perla Jazmin Abregos Chavez"}
{"index":{}}
{"id": 405, "name": "Piedad Eugenia Bedoya Rios"}
{"index":{}}
{"id": 406, "name": "Rafael De Jesus Gonzalez Santiago"}
{"index":{}}
{"id": 407, "name": "Raul Alberto Zuluaga Gutierrez"}
{"index":{}}
{"id": 408, "name": "Raul Eduardo Cardona Acevedo"}
{"index":{}}
{"id": 409, "name": "Raul Rueda Castaneda"}
{"index":{}}
{"id": 410, "name": "Reynaldo Daniel Rivera Palomino"}
{"index":{}}
{"id": 411, "name": "Ricardo Antonio Arango Restrepo"}
{"index":{}}
{"id": 412, "name": "Ricardo Osorio gonzalez"}
{"index":{}}
{"id": 413, "name": "Roberto Rafael De La Parra Lombana"}
{"index":{}}
{"id": 414, "name": "Rodolfo Ospina Rojas"}
{"index":{}}
{"id": 415, "name": "Rosa Liliana Vivas Auli"}
{"index":{}}
{"id": 417, "name": "Ruben Dario Munoz Arredondo"}
{"index":{}}
{"id": 418, "name": "Ruben Dario Triana Rodriguez"}
{"index":{}}
{"id": 419, "name": "Sacilnallely Perez Guzmán"}
{"index":{}}
{"id": 420, "name": "Sady Johanna Mejia Ramirez"}
{"index":{}}
{"id": 421, "name": "Sandra Paulina Ramirez Gallego"}
{"index":{}}
{"id": 422, "name": "Sandra Viviana Arias Andrade"}
{"index":{}}
{"id": 423, "name": "Santiago Alberto Velasquez Cadavid"}
{"index":{}}
{"id": 425, "name": "Santiago Cespedes Zapata"}
{"index":{}}
{"id": 426, "name": "Santiago Eduardo Morales Lopez"}
{"index":{}}
{"id": 427, "name": "Santiago Garces Gonzalez"}
{"index":{}}
{"id": 552, "name": "Santiago Osorio Duque"}
{"index":{}}
{"id": 428, "name": "Santiago Salamanca Lopez"}
{"index":{}}
{"id": 487, "name": "Santiago Tobon Cabrera"}
{"index":{}}
{"id": 431, "name": "Sebastian Mauricio Jimenez Caro"}
{"index":{}}
{"id": 491, "name": "Sebastian Sierra Ortiz"}
{"index":{}}
{"id": 432, "name": "Sebastian Velez Ruiz"}
{"index":{}}
{"id": 433, "name": "Sebastian Villa David"}
{"index":{}}
{"id": 434, "name": "Sergio Alejandro Salazar Gutierrez"}
{"index":{}}
{"id": 553, "name": "Sergio Andres Girado Arnedo"}
{"index":{}}
{"id": 435, "name": "Sergio Andres Lopez Pinto"}
{"index":{}}
{"id": 436, "name": "Sergio Andres Sierra Zuleta"}
{"index":{}}
{"id": 437, "name": "Sergio Velasquez Rendon"}
{"index":{}}
{"id": 438, "name": "Shirley Johanna Jaramillo Toro"}
{"index":{}}
{"id": 439, "name": "Silvana Londono Zapata"}
{"index":{}}
{"id": 441, "name": "Sonia Maria Berrio Valencia"}
{"index":{}}
{"id": 442, "name": "Sor Maria Acosta Benjumea"}
{"index":{}}
{"id": 443, "name": "Steven Alejandro Suarez Castro"}
{"index":{}}
{"id": 444, "name": "Suly Esperanza Ruiz Gutierrez"}
{"index":{}}
{"id": 445, "name": "Tatiana Andrea Echavarria Henao"}
{"index":{}}
{"id": 446, "name": "Tatiana Salazar Mesa"}
{"index":{}}
{"id": 447, "name": "Teresa De Jesus Marin Montes"}
{"index":{}}
{"id": 448, "name": "Vanessa Palacio Velez"}
{"index":{}}
{"id": 479, "name": "Victor Hugo Jimenez Torres"}
{"index":{}}
{"id": 450, "name": "Victor Manuel Catano Builes"}
{"index":{}}
{"id": 451, "name": "Walter Javier Alonso Roa"}
{"index":{}}
{"id": 452, "name": "Wbert Adrian Castro Vera"}
{"index":{}}
{"id": 453, "name": "Weimar Andres Castaneda Molina"}
{"index":{}}
{"id": 454, "name": "Wilfrank Montalvo Palacios"}
{"index":{}}
{"id": 455, "name": "William Anselmo Bautista Quesada"}
{"index":{}}
{"id": 456, "name": "William Enrique Palencia Romero"}
{"index":{}}
{"id": 525, "name": "Wilson Daniel Ospina Leon"}
{"index":{}}
{"id": 457, "name": "Wilson Fernando Rendon Henao"}
{"index":{}}
{"id": 458, "name": "Wilson Yamith Mendez Ramirez"}
{"index":{}}
{"id": 459, "name": "Yamir Asprilla Bedoya"}
{"index":{}}
{"id": 460, "name": "Yannick Blin"}
{"index":{}}
{"id": 461, "name": "Yeferson Frederman Marin Saldarriaga"}
{"index":{}}
{"id": 463, "name": "Yeison Ferney Canaveral Ramirez"}
{"index":{}}
{"id": 492, "name": "Yelitza Coromoto Farfan Angarita"}
{"index":{}}
{"id": 464, "name": "Yennifer Yurany Hoyos Salazar"}
{"index":{}}
{"id": 465, "name": "Yenny Trinidad Fierro Salgado"}
{"index":{}}
{"id": 466, "name": "Yesica Vargas Moreno"}
{"index":{}}
{"id": 467, "name": "Yesid Andres Balvin Sepulveda"}
{"index":{}}
{"id": 468, "name": "Yosel del Valle Pulgarin"}
{"index":{}}
{"id": 469, "name": "Yudy Viviana Leal Rodriguez"}
{"index":{}}
{"id": 470, "name": "Yuliana Andrea Tabares Quintero"}
{"index":{}}
{"id": 472, "name": "Yulieth Ximena Echeverri Perez"}
{"index":{}}
{"id": 473, "name": "Yury Alejandra Ramirez Bedoya"}
{"index":{}}
{"id": 474, "name": "Zindy Johana Martinez Garzon"}
{"index":{}}
{"id": 475, "name": "Zoraida Catalina Monsalve Cadavid"}
{"index":{}}
{"id": 477, "name": "Zulma Yaneth Fagua Herrera"}
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
