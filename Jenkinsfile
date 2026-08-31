pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK23'
    }

    stages {
        stage('Recuperation du code') {
            steps {
                checkout scm
            }
        }

        stage('Compilation') {
            steps {
                bat 'mvn -B compile'
            }
        }

        stage('Tests unitaires') {
            steps {
                bat 'mvn -B test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Empaquetage') {
            steps {
                bat 'mvn -B package -DskipTests'
            }
        }
    }

    post {
        success {
            echo 'Build reussi : compilation, tests et empaquetage OK.'
        }
        failure {
            echo 'Build en echec, voir les logs et le rapport de tests.'
        }
    }
}
