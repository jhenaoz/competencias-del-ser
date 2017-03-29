#!/bin/bash
curl -XDELETE "http://localhost:9200/person"
curl -XDELETE "http://localhost:9200/aptitude"
curl -XDELETE "http://localhost:9200/survey"
curl -XDELETE "http://localhost:9200/user"

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

curl -XPUT "http://localhost:9200/user" -d'
{
	"mappings": {
		"administrator": {
			"dynamic": "strict",
			"properties": {
				"username": {
					"type": "string"
				},
				"password": {
				  "type": "string"
				},
				"token": {
				  "type": "string"
				},
				"timestamp": {
				  "type": "date",
				  "format": "yyyy-MM-dd HH:mm:ss"
				}
			}
		}
	}
}'

curl -XPOST "http://localhost:9200/person/employee/_bulk?pretty" -d'
{"index":{}}
{"name": "Adolfo Valencia Jurado"}
{"index":{}}
{"name": "Adrián Yadir Jiménez Carrillo"}
{"index":{}}
{"name": "Adriana Maria Aguilar Viloria"}
{"index":{}}
{"name": "Adriana Maria Londono Galeano"}
{"index":{}}
{"name": "Alan Barbosa Mendoza"}
{"index":{}}
{"name": "Alberto Enrique Hernandez Sierra"}
{"index":{}}
{"name": "Alberto Giovanni Quintero Ortega"}
{"index":{}}
{"name": "Alejandra Maria Gomez Aristizabal"}
{"index":{}}
{"name": "Alejandra Maria Zuluaga Zapata"}
{"index":{}}
{"name": "Alejandro Bermudez Martinez"}
{"index":{}}
{"name": "Alejandro Calderon Velez"}
{"index":{}}
{"name": "Alejandro Franco Barrios"}
{"index":{}}
{"name": "Alejandro Ramirez Hernandez"}
{"index":{}}
{"name": "Alex Henry Giraldo Zea"}
{"index":{}}
{"name": "Alex William Moreno Rivas"}
{"index":{}}
{"name": "Alexander Botero Bustamante"}
{"index":{}}
{"name": "Aleyda Restrepo Cardona"}
{"index":{}}
{"name": "Alvaro Andres Castellanos Cervera"}
{"index":{}}
{"name": "Alvaro Uriel Montes Gomez"}
{"index":{}}
{"name": "Alveiro Garcia Nino"}
{"index":{}}
{"name": "Amaury Biquet"}
{"index":{}}
{"name": "Ana Lucia Aramburo Siegert"}
{"index":{}}
{"name": "Ana Lucia Castillo Ramirez"}
{"index":{}}
{"name": "Anamaria Ortiz Rodriguez"}
{"index":{}}
{"name": "Anderson Alexis Mejia Valencia"}
{"index":{}}
{"name": "Andrea Milena Morillo Cueto"}
{"index":{}}
{"name": "Andres Alberto Restrepo Herron"}
{"index":{}}
{"name": "Andres Camilo Amado Cardenas"}
{"index":{}}
{"name": "Andres Esteban Tamayo Uribe"}
{"index":{}}
{"name": "Andres Felipe Arrubla Zapata"}
{"index":{}}
{"name": "Andres Felipe De Leon Betancur"}
{"index":{}}
{"name": "Andres Felipe Gomez Rojas"}
{"index":{}}
{"name": "Andres Felipe Suarez Osorio"}
{"index":{}}
{"name": "Andres Fernando Arroyave Yepes"}
{"index":{}}
{"name": "Andres Mauricio Gonzalez Rojas"}
{"index":{}}
{"name": "Andres Mauricio Urrego Palacio"}
{"index":{}}
{"name": "Andres Sanchez Munoz"}
{"index":{}}
{"name": "Angela Maria Bustamante Restrepo"}
{"index":{}}
{"name": "Angela Maria Patarroyo Montenegro"}
{"index":{}}
{"name": "Angelica Maria Castaneda Villamil"}
{"index":{}}
{"name": "Ariel Eduardo Pena Ocando"}
{"index":{}}
{"name": "Astrid Riveros Pardo"}
{"index":{}}
{"name": "Beatriz Elena Sanchez Trochez"}
{"index":{}}
{"name": "Camilo Alberto Gutierrez Vargas"}
{"index":{}}
{"name": "Camilo Andres Varela Leon"}
{"index":{}}
{"name": "Camilo Andres Velez Gutierrez"}
{"index":{}}
{"name": "Camilo Velasquez Taborda"}
{"index":{}}
{"name": "Carlos Alberto Londono Perez"}
{"index":{}}
{"name": "Carlos Alberto Quiroz Builes"}
{"index":{}}
{"name": "Carlos Alfredo Suarez Berrio"}
{"index":{}}
{"name": "Carlos Andres Arboleda Zapata"}
{"index":{}}
{"name": "Carlos Andres Arboleda Zapata"}
{"index":{}}
{"name": "Carlos Andres Cardenas Pachon"}
{"index":{}}
{"name": "Carlos Andres Hernandez Eusse"}
{"index":{}}
{"name": "Carlos Andres Zapata Cuervo"}
{"index":{}}
{"name": "Carlos Eduardo Tovar Pinto"}
{"index":{}}
{"name": "Carlos Esteban Perez Uribe"}
{"index":{}}
{"name": "Carlos Guillermo Duarte Bautista"}
{"index":{}}
{"name": "Carlos Harim Perez Rodriguez"}
{"index":{}}
{"name": "Carlos Mario Bedoya Munera"}
{"index":{}}
{"name": "Carmen Rosa Ruidiaz Martinez"}
{"index":{}}
{"name": "Carolina Arboleda Gomez"}
{"index":{}}
{"name": "Carolina Contreras Landinez"}
{"index":{}}
{"name": "Carolina Correa Orozco"}
{"index":{}}
{"name": "Carolina Echeverri Cardona"}
{"index":{}}
{"name": "Cartlos Andres Arboleda Zapata"}
{"index":{}}
{"name": "Cartlos Andres Arboleda Zapata"}
{"index":{}}
{"name": "Catalina Cordoba Charria"}
{"index":{}}
{"name": "Catalina Gomez Alzate"}
{"index":{}}
{"name": "Catalina Soto Zuluaga"}
{"index":{}}
{"name": "Cesar Andres Vargas Parra"}
{"index":{}}
{"name": "Cesar Armando Villalobos Rubiano"}
{"index":{}}
{"name": "Cesar Augusto Gomez Bustamante"}
{"index":{}}
{"name": "Cesar Augusto Mosquera Palacios"}
{"index":{}}
{"name": "Cesar Manuel Romero Arroyo"}
{"index":{}}
{"name": "Claudia Elena Gaviria Bedoya"}
{"index":{}}
{"name": "Cristhian Giovanni Guevara Alarcon"}
{"index":{}}
{"name": "Cristhian Mauricio Preciado Hernandez"}
{"index":{}}
{"name": "Cristian Camilo Molina Mendoza"}
{"index":{}}
{"name": "Cristian Camilo Munoz Martinez"}
{"index":{}}
{"name": "Cristian Duvan Ganan Pena"}
{"index":{}}
{"name": "Cyndi Jeanette Carvajal Calle"}
{"index":{}}
{"name": "Daniel Alberto Martinez Galvis"}
{"index":{}}
{"name": "Daniel Correa Barrios"}
{"index":{}}
{"name": "Daniel Esteban Fuentes Gutierrez"}
{"index":{}}
{"name": "Daniel Esteban Valderrama Tangarife"}
{"index":{}}
{"name": "Daniel Isaza Jimenez"}
{"index":{}}
{"name": "Daniel Jose Ramirez Valencia"}
{"index":{}}
{"name": "Daniel Ricaurte Castano Jaramillo"}
{"index":{}}
{"name": "Daniel Stiven Restrepo Arcila"}
{"index":{}}
{"name": "Daniela Mancilla Valdes"}
{"index":{}}
{"name": "Daniela Yepes Gaviria"}
{"index":{}}
{"name": "Danilo Octavio Alarcon Rodriguez"}
{"index":{}}
{"name": "Danny Alberto Londono Henao"}
{"index":{}}
{"name": "Darwin Ramos Cuervo"}
{"index":{}}
{"name": "David Alberto Moreno Palacio"}
{"index":{}}
{"name": "David Alejandro Florez Restrepo"}
{"index":{}}
{"name": "David Alejandro Lopez Zapata"}
{"index":{}}
{"name": "David Alejandro Mona Palacio"}
{"index":{}}
{"name": "David Alejandro Morales Valencia"}
{"index":{}}
{"name": "David Andres Bedoya Hernandez"}
{"index":{}}
{"name": "David Andres Hurtado Rodriguez"}
{"index":{}}
{"name": "David Bohorquez Alvarez"}
{"index":{}}
{"name": "David Brayan Villa Reyes"}
{"index":{}}
{"name": "David Camilo Heredia Ardila"}
{"index":{}}
{"name": "David Camilo Serrano Abril"}
{"index":{}}
{"name": "David Camilo Torres Eraso"}
{"index":{}}
{"name": "David Esteban Escobar Gutierrez"}
{"index":{}}
{"name": "David Franco Chica"}
{"index":{}}
{"name": "David Julian Cardona Quintero"}
{"index":{}}
{"name": "David Monsalve Galeano"}
{"index":{}}
{"name": "David Stiven Velez Segura"}
{"index":{}}
{"name": "David Vasquez Rivera"}
{"index":{}}
{"name": "Deibys Fernando Quintero Consuegra"}
{"index":{}}
{"name": "Delia Pineda Mejorada"}
{"index":{}}
{"name": "Derly Katerine Echavarria"}
{"index":{}}
{"name": "Diana Alexandra Parra Perilla"}
{"index":{}}
{"name": "Diana Angelica Cardona Marin"}
{"index":{}}
{"name": "Diana Lucia Castro Ortega"}
{"index":{}}
{"name": "Diana Milena Davila Pedraza"}
{"index":{}}
{"name": "Diana Patricia Diaz Ramirez"}
{"index":{}}
{"name": "Didier Alberto Tabares Higuita"}
{"index":{}}
{"name": "Diego Alejandro Ruiz Gutierrez"}
{"index":{}}
{"name": "Diego Alejandro Trejos Trujillo"}
{"index":{}}
{"name": "Diego Alexander Hoyos David"}
{"index":{}}
{"name": "Diego Andres Gonzalez Galvis"}
{"index":{}}
{"name": "Diego Armando Bonilla Buritica"}
{"index":{}}
{"name": "Diego Armando Farfan Barrero"}
{"index":{}}
{"name": "Diego Hernan Montoya Bedoya"}
{"index":{}}
{"name": "Diego Leon Betancur Catano"}
{"index":{}}
{"name": "Diego Miguel Patino Rodriguez"}
{"index":{}}
{"name": "Dilson Alzate Perez"}
{"index":{}}
{"name": "Diomedes Igua Hernandez"}
{"index":{}}
{"name": "Dora Catalina Villada Arango"}
{"index":{}}
{"name": "Dora Luz Ocampo Goez"}
{"index":{}}
{"name": "Duvan Armando Moreno Marin"}
{"index":{}}
{"name": "Duvan Bernardo Cardenas Galeano"}
{"index":{}}
{"name": "Eder Marceno Cotes Solano"}
{"index":{}}
{"name": "Eder Navarro Martinez"}
{"index":{}}
{"name": "Eder Noe Bonilla Enriquez"}
{"index":{}}
{"name": "Edgar Alejandro Palacio Duque"}
{"index":{}}
{"name": "Edgar Andres Alvarez Bedoya"}
{"index":{}}
{"name": "Edgar Andres Marin Vega"}
{"index":{}}
{"name": "Edison Andres Castro Tabares"}
{"index":{}}
{"name": "Edna Patricia Mosquera Lopez"}
{"index":{}}
{"name": "Edwin Alberto Diaz Echeverri"}
{"index":{}}
{"name": "Edwin Alberto Posada Giraldo"}
{"index":{}}
{"name": "Edwin Escudero Restrepo"}
{"index":{}}
{"name": "Edwin Mantilla Santamaria"}
{"index":{}}
{"name": "Elizabeth Quintero Gonzalez"}
{"index":{}}
{"name": "Elsy Jhoanna Cortes Vega"}
{"index":{}}
{"name": "Elvira Cuartas Ospina"}
{"index":{}}
{"name": "Elvis Alirio Astaiza Gutierrez"}
{"index":{}}
{"name": "Eric Berchem Caldera"}
{"index":{}}
{"name": "Erika Alejandra Betancur Buritica"}
{"index":{}}
{"name": "Esteban Molina Abad"}
{"index":{}}
{"name": "Esteban Vasquez Gonzalez"}
{"index":{}}
{"name": "Ever Alonso Echeverri Velasquez"}
{"index":{}}
{"name": "Ever Alonso Torres Galeano"}
{"index":{}}
{"name": "Fabian Alexis Becerra Monsalve"}
{"index":{}}
{"name": "Fabian Esteban Herrera Herrera"}
{"index":{}}
{"name": "Fabio Mauricio Gallo Parra"}
{"index":{}}
{"name": "Fabrizio Sgura"}
{"index":{}}
{"name": "Faricelly Restrepo Cuartas"}
{"index":{}}
{"name": "Felipe Velasquez Uribe"}
{"index":{}}
{"name": "Fernando De Jesus Castilla Ospina"}
{"index":{}}
{"name": "Flor Maria Bedoya Lopez"}
{"index":{}}
{"name": "Francisco Javier Gaviria Sierra"}
{"index":{}}
{"name": "Francisco Luis Rodriguez Villabona"}
{"index":{}}
{"name": "Frederic Yesid Pena Sanchez"}
{"index":{}}
{"name": "Fredy Alexander Bedoya Soto"}
{"index":{}}
{"name": "Gabriel Jaime Hurtado Canola"}
{"index":{}}
{"name": "Gabriel Jaime Moreno Maya"}
{"index":{}}
{"name": "German David Potes Franco"}
{"index":{}}
{"name": "Gilma Yaneth Garcia Henao"}
{"index":{}}
{"name": "Gina Maria Arcieri Pulgarin"}
{"index":{}}
{"name": "Giovany Stik Tovar Garcia"}
{"index":{}}
{"name": "Giuseppe Fernando Caro Rodriguez"}
{"index":{}}
{"name": "Gladys Cecilia Giraldo Giraldo"}
{"index":{}}
{"name": "Gloria Maria Aguirre Sepulveda"}
{"index":{}}
{"name": "Gloria Patricia Velez Isaza"}
{"index":{}}
{"name": "Gregorio Alejandro Aristizabal Escobar"}
{"index":{}}
{"name": "Gustavo Adolfo Arroyave Lopez"}
{"index":{}}
{"name": "Gustavo Adolfo Duque Osorio"}
{"index":{}}
{"name": "Gustavo Adolfo Munoz Aguilar"}
{"index":{}}
{"name": "Gustavo Eduardo Mendoza Ramirez"}
{"index":{}}
{"name": "Gustavo Gonzalez Valencia"}
{"index":{}}
{"name": "Haiver Carrillo Castro"}
{"index":{}}
{"name": "Hakan Gosta Ahlstedt"}
{"index":{}}
{"name": "Heidy Tatiana Tamayo Fernandez"}
{"index":{}}
{"name": "Hernan Alfonso Cortes Navarro"}
{"index":{}}
{"name": "Hernan Dario Mesa Roldan"}
{"index":{}}
{"name": "Hernan Diaz Rodriguez"}
{"index":{}}
{"name": "Hernan Lopez Vergara"}
{"index":{}}
{"name": "Hogly Leonardo Rubio Tarazona"}
{"index":{}}
{"name": "Holmes Giovanny Salazar Osorio"}
{"index":{}}
{"name": "Ingri Johana Castaneda Cruz"}
{"index":{}}
{"name": "Irene Amparo Giraldo Naranjo"}
{"index":{}}
{"name": "Israel Alejandro Perdomo Bolanos"}
{"index":{}}
{"name": "Jackson Alexis Palacios Mosquera"}
{"index":{}}
{"name": "Jacobo Asmar Meza"}
{"index":{}}
{"name": "Jaiber Mauricio Suarez Robelto"}
{"index":{}}
{"name": "Jaime Alberto Carmona Arango"}
{"index":{}}
{"name": "Jaime Andres Betancur Duque"}
{"index":{}}
{"name": "Jairo Andres Gonzalez Franco"}
{"index":{}}
{"name": "Jairo Eduardo Ipial Villamil"}
{"index":{}}
{"name": "Jairo Leon Zapata Sepulveda"}
{"index":{}}
{"name": "Javier Antonio Navarro Suarez"}
{"index":{}}
{"name": "Javier Eduardo Jaimes Velasquez"}
{"index":{}}
{"name": "Javier Restrepo Rendon"}
{"index":{}}
{"name": "Jean Sebastian Ruiz Rojas"}
{"index":{}}
{"name": "Jeisson Ferney Delgado Cuentas"}
{"index":{}}
{"name": "Jenifer Johana Acosta Gallego"}
{"index":{}}
{"name": "Jenny Alejandra Florez Torres"}
{"index":{}}
{"name": "Jeyson Oswaldo Gallego Villegas"}
{"index":{}}
{"name": "Jhenny Leandra Giraldo Trujillo"}
{"index":{}}
{"name": "Jhon Arley Osorio Sepulveda"}
{"index":{}}
{"name": "Jhon Edison Henao Vega"}
{"index":{}}
{"name": "Jhon Edward Murillo Reyes"}
{"index":{}}
{"name": "Jhon Faber Echavez Posada"}
{"index":{}}
{"name": "Jhonatan Gonzalez Lozano"}
{"index":{}}
{"name": "Jhonathan Olaya Garro"}
{"index":{}}
{"name": "Jhonnatan Pulgarin Blandon"}
{"index":{}}
{"name": "Jimena Del Pilar Rincon Cruz"}
{"index":{}}
{"name": "Joaquin David Hernandez Cardenas"}
{"index":{}}
{"name": "Johan Abril Animero"}
{"index":{}}
{"name": "Johan Miguel Ruiz Rodriguez"}
{"index":{}}
{"name": "Johan Smith Gutierrez Giraldo"}
{"index":{}}
{"name": "Johana Sirley Rodriguez Patino"}
{"index":{}}
{"name": "Johann Nicolas Diaz Montero"}
{"index":{}}
{"name": "John Alexander Alzate"}
{"index":{}}
{"name": "John Alexander De La Pava Pena"}
{"index":{}}
{"name": "John Antonny Jaramillo Mora"}
{"index":{}}
{"name": "John Mauricio Ruiz Herrera"}
{"index":{}}
{"name": "Johnny Alexander Jaramillo Gallego"}
{"index":{}}
{"name": "Jonathan Alexander Acevedo Galeano"}
{"index":{}}
{"name": "Jonathan Hernandez Zapata"}
{"index":{}}
{"name": "Jorge Andres Holguin Medina"}
{"index":{}}
{"name": "Jorge Antonio Moreno Rodriguez"}
{"index":{}}
{"name": "Jorge Hernan Aramburo Siegert"}
{"index":{}}
{"name": "Jose Alejandro Nino Mora"}
{"index":{}}
{"name": "Jose Andres Oliveros Echeverry"}
{"index":{}}
{"name": "Jose Andres Sanchez Moreno"}
{"index":{}}
{"name": "Jose Cruz De Ita Reyes"}
{"index":{}}
{"name": "Jose Guillermo Valle Pavas"}
{"index":{}}
{"name": "Jose Luis Lopez Hernandez"}
{"index":{}}
{"name": "Jose Rafael Arrieta Dominguez"}
{"index":{}}
{"name": "Jose Walter Sierra Jaramillo"}
{"index":{}}
{"name": "Josias Daniel Montoya Campo"}
{"index":{}}
{"name": "Juan Camilo Arias Tamayo"}
{"index":{}}
{"name": "Juan Camilo Gomez Aristizabal"}
{"index":{}}
{"name": "Juan Camilo Morales Nino"}
{"index":{}}
{"name": "Juan Camilo Vallejo Restrepo"}
{"index":{}}
{"name": "Juan Carlos Alejandro Vasquez Aramburo"}
{"index":{}}
{"name": "Juan Carlos Giron Salazar"}
{"index":{}}
{"name": "Juan Carlos Rodriguez Lara"}
{"index":{}}
{"name": "Juan Carlos Torres Hernandez"}
{"index":{}}
{"name": "Juan Daniel Ortiz Garcia"}
{"index":{}}
{"name": "Juan David Cano Arboleda"}
{"index":{}}
{"name": "Juan David Cardona Cardona"}
{"index":{}}
{"name": "Juan David Gonzalez Piza"}
{"index":{}}
{"name": "Juan David Henao Zapata"}
{"index":{}}
{"name": "Juan David Munoz Velez"}
{"index":{}}
{"name": "Juan David Naranjo Arias"}
{"index":{}}
{"name": "Juan Esteban Alvarez Bustamante"}
{"index":{}}
{"name": "Juan Esteban Guerra Echeverry"}
{"index":{}}
{"name": "Juan Esteban Herrera Morales"}
{"index":{}}
{"name": "Juan Esteban Lopez Tamayo"}
{"index":{}}
{"name": "Juan Esteban Moncada Castano"}
{"index":{}}
{"name": "Juan Esteban Torres Solano"}
{"index":{}}
{"name": "Juan Felipe Builes Gallo"}
{"index":{}}
{"name": "Juan Felipe Ocampo Bedoya"}
{"index":{}}
{"name": "Juan Fernando Diaz Delgado"}
{"index":{}}
{"name": "Juan Fernando Londono Gomez"}
{"index":{}}
{"name": "Juan Fernando Moreno Marin"}
{"index":{}}
{"name": "Juan Fernando Valencia Toro"}
{"index":{}}
{"name": "Juan Fernando Velasquez Martinez"}
{"index":{}}
{"name": "Juan Gabriel Gomez Baron"}
{"index":{}}
{"name": "Juan Gabriel Romero Silva"}
{"index":{}}
{"name": "Juan Guillermo Gomez Montoya"}
{"index":{}}
{"name": "Juan Jose Uribe Lopez"}
{"index":{}}
{"name": "Juan Leonardo Trillos Gutierrez"}
{"index":{}}
{"name": "Juan Pablo Bonilla Chavez"}
{"index":{}}
{"name": "Juan Pablo Casas Chica"}
{"index":{}}
{"name": "Juan Pablo Franco Gomez"}
{"index":{}}
{"name": "Juan Sebastian Duenas Urrutia"}
{"index":{}}
{"name": "Juan Sebastian Naffah Isaza"}
{"index":{}}
{"name": "Juan Sebastian Perez Alvarez"}
{"index":{}}
{"name": "Juan Sebastian Sanchez Castillo"}
{"index":{}}
{"name": "Juan Sebastian Zapata Tamayo"}
{"index":{}}
{"name": "Juan Vicente Pena Peralta"}
{"index":{}}
{"name": "Julian Alberto Duque Cespedes"}
{"index":{}}
{"name": "Julian Andres Montoya Gaviria"}
{"index":{}}
{"name": "Julian Andres Soto Arcila"}
{"index":{}}
{"name": "Julian Eugenio Loaiza Ruiz"}
{"index":{}}
{"name": "Julian Stiven Garzon Acevedo"}
{"index":{}}
{"name": "Julian Vanegas Piedrahita"}
{"index":{}}
{"name": "Juliana Garcia Manrique"}
{"index":{}}
{"name": "Juliana Maria Cruz Echeverri"}
{"index":{}}
{"name": "Julie Cicely Ruiz Aguilera"}
{"index":{}}
{"name": "Juliet Natalia Cardenas Munoz"}
{"index":{}}
{"name": "Karolina Kaufman Porat"}
{"index":{}}
{"name": "Katerine Villamizar Suaza"}
{"index":{}}
{"name": "Katherine Gomez Arrieta"}
{"index":{}}
{"name": "Lamia Josefa Nader Ordonez"}
{"index":{}}
{"name": "Laura Bedoya Jaramillo"}
{"index":{}}
{"name": "Laura Catalina Navarro Rodriguez"}
{"index":{}}
{"name": "Laura Catalina Vanegas Balvin"}
{"index":{}}
{"name": "Laura Daniela Mejia Garcia"}
{"index":{}}
{"name": "Laura Lizeth Escobar Ospina"}
{"index":{}}
{"name": "Laura Pareja Perez"}
{"index":{}}
{"name": "Laura Vanessa Hernandez Benitez"}
{"index":{}}
{"name": "Lecxy Rocio Alzate Uribe"}
{"index":{}}
{"name": "Leidy Joana Alvarez Ramirez"}
{"index":{}}
{"name": "Leidy Viviana Castro Marin"}
{"index":{}}
{"name": "Leiner Fabian Orrego Giraldo"}
{"index":{}}
{"name": "Leonardo Andres Munoz Henao"}
{"index":{}}
{"name": "Leonardo Jimenez Gomez"}
{"index":{}}
{"name": "Lia Sanchez Echeverri"}
{"index":{}}
{"name": "Liliam Paola Bolanos Rengifo"}
{"index":{}}
{"name": "Lina Marcela Cadavid Vasquez"}
{"index":{}}
{"name": "Lina Marcela Monsalve Higuita"}
{"index":{}}
{"name": "Lina Maria Gomez Sierra"}
{"index":{}}
{"name": "Lina Maria Jaramillo Lopera"}
{"index":{}}
{"name": "Linda Vanessa Zapata Henao"}
{"index":{}}
{"name": "Lindelia Nieto Ramirez"}
{"index":{}}
{"name": "Lizeth Johana Mendoza Vergara"}
{"index":{}}
{"name": "Lucy Beatriz Tobias Escudero"}
{"index":{}}
{"name": "Luis Alberto Bolanos Salazar"}
{"index":{}}
{"name": "Luis Alfonso Roca Rosero"}
{"index":{}}
{"name": "Luis Alfredo Alcaraz Moreno"}
{"index":{}}
{"name": "Luis Antonio Cervantes Ortega"}
{"index":{}}
{"name": "Luis Carlos Torres Castro"}
{"index":{}}
{"name": "Luis David Carreno Henao"}
{"index":{}}
{"name": "Luis Eduardo Blandon Puello"}
{"index":{}}
{"name": "Luis Felipe Acosta Mira"}
{"index":{}}
{"name": "Luis Fernando Garcia Rodriguez"}
{"index":{}}
{"name": "Luis Fernando Hernandez Fernandez"}
{"index":{}}
{"name": "Luis Hernando Salas Manrique"}
{"index":{}}
{"name": "Luis Miguel Piedrahita Londono"}
{"index":{}}
{"name": "Luis Miguel Ramirez Lastra"}
{"index":{}}
{"name": "Luis Santiago Alvear Angel"}
{"index":{}}
{"name": "Luz Angela Perona Ossaba"}
{"index":{}}
{"name": "Luz Janeth Castano Osorio"}
{"index":{}}
{"name": "Luz Johanna Ariza Pardo"}
{"index":{}}
{"name": "Luz Marleny Arroyave Norena"}
{"index":{}}
{"name": "Luz Mary Gonzalez Castillo"}
{"index":{}}
{"name": "Luz Stella Cuervo Restrepo"}
{"index":{}}
{"name": "Manuel Alejandro Forero Ortegon"}
{"index":{}}
{"name": "Manuel Felipe Cabrales Azcarate"}
{"index":{}}
{"name": "Manuel Fernando Gallego Arias"}
{"index":{}}
{"name": "Marco Fernando Reyes Valderrama"}
{"index":{}}
{"name": "Marcos Enrique Manrique Pimienta"}
{"index":{}}
{"name": "Margarita Maria Arango Suarez"}
{"index":{}}
{"name": "Maria Alejandra Pulido Munoz"}
{"index":{}}
{"name": "Maria Alejandra Rodas Sanchez"}
{"index":{}}
{"name": "Maria Alejandra Torres Avellaneda"}
{"index":{}}
{"name": "Maria Angelica Rivas Sanabria"}
{"index":{}}
{"name": "Maria Catalina Torres Maturana"}
{"index":{}}
{"name": "Maria Clara Martinez Mendez"}
{"index":{}}
{"name": "Maria Cristina Arango Wiedemann"}
{"index":{}}
{"name": "Maria Ines Giraldo Lopez"}
{"index":{}}
{"name": "Maria Isabel Ruiz Henao"}
{"index":{}}
{"name": "Maria Luisa Fernanda Avendano Jimenez"}
{"index":{}}
{"name": "Maria Margarita Hoyos Cancimanse"}
{"index":{}}
{"name": "Maria Paula Cabra Silva"}
{"index":{}}
{"name": "Maria Soledad Bustamante Restrepo"}
{"index":{}}
{"name": "Maria Terese Ranallo"}
{"index":{}}
{"name": "Mariana Ramirez Atehortua"}
{"index":{}}
{"name": "Maribel Gomez Ramirez"}
{"index":{}}
{"name": "Maribel Moreno Cubides"}
{"index":{}}
{"name": "Mario Arango Duque"}
{"index":{}}
{"name": "Mario Garcia Hernandez"}
{"index":{}}
{"name": "Marisol Garcia Giraldo"}
{"index":{}}
{"name": "Marleyudemi Ruiz Torres"}
{"index":{}}
{"name": "Marlon Andrei Aguilar Velez"}
{"index":{}}
{"name": "Marlon Danilo Montano Rodriguez"}
{"index":{}}
{"name": "Marta Lucia Vanbruggen Benjumea"}
{"index":{}}
{"name": "Martha Patricia Sandoval Toro"}
{"index":{}}
{"name": "Mateo Delgado Patino"}
{"index":{}}
{"name": "Mauricio Diaz Rodriguez"}
{"index":{}}
{"name": "Mauricio Lopez Soto"}
{"index":{}}
{"name": "Mauricio Rivera Montoya"}
{"index":{}}
{"name": "Melina Alejandra Arevalo Arevalo"}
{"index":{}}
{"name": "Melissa Espinal Lopez"}
{"index":{}}
{"name": "Miguel Alejandro Huertas Ayala"}
{"index":{}}
{"name": "Miguel Angel Duarte Herrera"}
{"index":{}}
{"name": "Miguel Angel Rodriguez Guerra"}
{"index":{}}
{"name": "Miguel Eduardo Osorio Montilla"}
{"index":{}}
{"name": "Miguel Esteban Betin Montes"}
{"index":{}}
{"name": "Milton Cesar Perez Velasquez"}
{"index":{}}
{"name": "Monica Cristina Abreo Ariza"}
{"index":{}}
{"name": "Monica Gil Castro"}
{"index":{}}
{"name": "Monica Patricia Uribe Rozo"}
{"index":{}}
{"name": "Myriam Ramos Garzon"}
{"index":{}}
{"name": "Natalia Fernandez Posada"}
{"index":{}}
{"name": "Natalia Hernandez Betancur"}
{"index":{}}
{"name": "Natalia Rivas Restrepo"}
{"index":{}}
{"name": "Nestor Eduardo Fajardo Rodriguez"}
{"index":{}}
{"name": "Nicolas David Giraldo Yepes"}
{"index":{}}
{"name": "Nidian Suarez Talero"}
{"index":{}}
{"name": "Norman Danilo Castro Tellez"}
{"index":{}}
{"name": "Nury Natalia Toro Gonzalez"}
{"index":{}}
{"name": "Olga Beatriz Naranjo Giraldo"}
{"index":{}}
{"name": "Olga Lucia Ramirez Granda"}
{"index":{}}
{"name": "Oreida Ruiz Molina"}
{"index":{}}
{"name": "Orlando Villabona Bolanos"}
{"index":{}}
{"name": "Oscar Dario Malagon Murcia"}
{"index":{}}
{"name": "Oscar Eduardo Sepulveda Montoya"}
{"index":{}}
{"name": "Oscar Gustavo Morales Riasco"}
{"index":{}}
{"name": "Oscar Hernan Guevara Gil"}
{"index":{}}
{"name": "Oscar Ivan Lopez Pulido"}
{"index":{}}
{"name": "Oscar Sandoval Ramos"}
{"index":{}}
{"name": "Oscar Yesid Anaya Oviedo"}
{"index":{}}
{"name": "Oskar Adrian Leon Guavita"}
{"index":{}}
{"name": "Otto Ricardo Navarrette Botia"}
{"index":{}}
{"name": "Pablo Andres Acosta Amaya"}
{"index":{}}
{"name": "Pablo Andres Alzate Bedoya"}
{"index":{}}
{"name": "Paola Andrea Gonzalez Medina"}
{"index":{}}
{"name": "Pedro Jose Albor Barros"}
{"index":{}}
{"name": "Pedro Luis Patron Espitia"}
{"index":{}}
{"name": "Pedro Madrid Barbotto"}
{"index":{}}
{"name": "Peggy Roxana Giraldo Cordoba"}
{"index":{}}
{"name": "Perla Jazmin Abregos Chavez"}
{"index":{}}
{"name": "Piedad Eugenia Bedoya Rios"}
{"index":{}}
{"name": "Rafael De Jesus Gonzalez Santiago"}
{"index":{}}
{"name": "Raul Alberto Zuluaga Gutierrez"}
{"index":{}}
{"name": "Raul Eduardo Cardona Acevedo"}
{"index":{}}
{"name": "Raul Rueda Castaneda"}
{"index":{}}
{"name": "Reynaldo Daniel Rivera Palomino"}
{"index":{}}
{"name": "Ricardo Antonio Arango Restrepo"}
{"index":{}}
{"name": "Ricardo Osorio gonzalez"}
{"index":{}}
{"name": "Roberto Rafael De La Parra Lombana"}
{"index":{}}
{"name": "Rodolfo Ospina Rojas"}
{"index":{}}
{"name": "Rosa Liliana Vivas Auli"}
{"index":{}}
{"name": "Ruben Dario Munoz Arredondo"}
{"index":{}}
{"name": "Ruben Dario Triana Rodriguez"}
{"index":{}}
{"name": "Sacilnallely Perez Guzmán"}
{"index":{}}
{"name": "Sady Johanna Mejia Ramirez"}
{"index":{}}
{"name": "Sandra Paulina Ramirez Gallego"}
{"index":{}}
{"name": "Sandra Viviana Arias Andrade"}
{"index":{}}
{"name": "Santiago Alberto Velasquez Cadavid"}
{"index":{}}
{"name": "Santiago Cespedes Zapata"}
{"index":{}}
{"name": "Santiago Eduardo Morales Lopez"}
{"index":{}}
{"name": "Santiago Garces Gonzalez"}
{"index":{}}
{"name": "Santiago Osorio Duque"}
{"index":{}}
{"name": "Santiago Salamanca Lopez"}
{"index":{}}
{"name": "Santiago Tobon Cabrera"}
{"index":{}}
{"name": "Sebastian Mauricio Jimenez Caro"}
{"index":{}}
{"name": "Sebastian Sierra Ortiz"}
{"index":{}}
{"name": "Sebastian Velez Ruiz"}
{"index":{}}
{"name": "Sebastian Villa David"}
{"index":{}}
{"name": "Sergio Alejandro Salazar Gutierrez"}
{"index":{}}
{"name": "Sergio Andres Girado Arnedo"}
{"index":{}}
{"name": "Sergio Andres Lopez Pinto"}
{"index":{}}
{"name": "Sergio Andres Sierra Zuleta"}
{"index":{}}
{"name": "Sergio Velasquez Rendon"}
{"index":{}}
{"name": "Shirley Johanna Jaramillo Toro"}
{"index":{}}
{"name": "Silvana Londono Zapata"}
{"index":{}}
{"name": "Sonia Maria Berrio Valencia"}
{"index":{}}
{"name": "Sor Maria Acosta Benjumea"}
{"index":{}}
{"name": "Steven Alejandro Suarez Castro"}
{"index":{}}
{"name": "Suly Esperanza Ruiz Gutierrez"}
{"index":{}}
{"name": "Tatiana Andrea Echavarria Henao"}
{"index":{}}
{"name": "Tatiana Salazar Mesa"}
{"index":{}}
{"name": "Teresa De Jesus Marin Montes"}
{"index":{}}
{"name": "Vanessa Palacio Velez"}
{"index":{}}
{"name": "Victor Hugo Jimenez Torres"}
{"index":{}}
{"name": "Victor Manuel Catano Builes"}
{"index":{}}
{"name": "Walter Javier Alonso Roa"}
{"index":{}}
{"name": "Wbert Adrian Castro Vera"}
{"index":{}}
{"name": "Weimar Andres Castaneda Molina"}
{"index":{}}
{"name": "Wilfrank Montalvo Palacios"}
{"index":{}}
{"name": "William Anselmo Bautista Quesada"}
{"index":{}}
{"name": "William Enrique Palencia Romero"}
{"index":{}}
{"name": "Wilson Daniel Ospina Leon"}
{"index":{}}
{"name": "Wilson Fernando Rendon Henao"}
{"index":{}}
{"name": "Wilson Yamith Mendez Ramirez"}
{"index":{}}
{"name": "Yamir Asprilla Bedoya"}
{"index":{}}
{"name": "Yannick Blin"}
{"index":{}}
{"name": "Yeferson Frederman Marin Saldarriaga"}
{"index":{}}
{"name": "Yeison Ferney Canaveral Ramirez"}
{"index":{}}
{"name": "Yelitza Coromoto Farfan Angarita"}
{"index":{}}
{"name": "Yennifer Yurany Hoyos Salazar"}
{"index":{}}
{"name": "Yenny Trinidad Fierro Salgado"}
{"index":{}}
{"name": "Yesica Vargas Moreno"}
{"index":{}}
{"name": "Yesid Andres Balvin Sepulveda"}
{"index":{}}
{"name": "Yosel del Valle Pulgarin"}
{"index":{}}
{"name": "Yudy Viviana Leal Rodriguez"}
{"index":{}}
{"name": "Yuliana Andrea Tabares Quintero"}
{"index":{}}
{"name": "Yulieth Ximena Echeverri Perez"}
{"index":{}}
{"name": "Yury Alejandra Ramirez Bedoya"}
{"index":{}}
{"name": "Zindy Johana Martinez Garzon"}
{"index":{}}
{"name": "Zoraida Catalina Monsalve Cadavid"}
{"index":{}}
{"name": "Zulma Yaneth Fagua Herrera"}
'

curl -XPOST "http://localhost:9200/aptitude/aptitude/_bulk?pretty" -d'
{"index":{"_id": "1"}}
{"id":1, "es": "Apertura","en": "Openness", "behaviors": [{"id": "1", "es": "Acepta sugerencias sin distinción de quien las dé", "en": "They accept suggestions regardless of who gives them"}, {"id": "2", "es": "Reconoce sus errores", "en": "They recognize their mistakes"}, {"id": "3", "es": "Solicita sugerencias", "en": "They ask for suggestions"}, {"id": "4", "es": "Se evidencian sus acciones de mejora", "en": "Their actions towards improvement are apparent"}, {"id": "5", "es": "Informa oportunamente situaciones que representan riesgos u obstáculos para el equipo", "en": "They opportunely inform of situations that represent risks and obstacles for the team"}]}
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

curl -XPOST "http://localhost:9200/user/administrator/1" -d'
{
  "username":"admin",
  "password":"ac6d4c32be1e5e250711e0ad9e813e5d44c9588609b46b267fcd7373354c5baca71c4b7f4c358c4cdcc644fd27624168e9e6e141cd445410496662f10d35cae8"
}'