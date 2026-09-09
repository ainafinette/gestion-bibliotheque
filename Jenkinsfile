pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK21'
    }

    stages {
        stage('Recuperation du code') {
            steps {
                checkout scm
            }
        }

        stage('Compilation & Tests & Rapport de couverture') {
            steps {
                // "verify" lance les tests ET génère le rapport JaCoCo
                bat 'mvn -B verify'
            }
            post {
                always {
                    // Publie les résultats des tests JUnit
                    junit 'target/surefire-reports/*.xml'
                    // Publie le rapport JaCoCo (nécessite le plugin "HTML Publisher" installé dans Jenkins)
                    publishHTML([
                        reportDir: 'target/site/jacoco',
                        reportFiles: 'index.html',
                        reportName: 'Rapport de couverture JaCoCo'
                    ])
                }
            }
        }

        stage('Empaquetage') {
            steps {
                // On a déjà fait le package dans verify, mais on peut le refaire si besoin
                bat 'mvn -B package -DskipTests'
            }
        }
    }

    post {
        success {
            echo 'Build reussi : compilation, tests, couverture et empaquetage OK.'
        }
        failure {
            echo 'Build en echec, voir les logs et le rapport de tests.'
        }
    }
}