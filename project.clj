(defproject tv-api "0.1.0-SNAPSHOT"
  :description "Demo API using Compojure"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [metosin/compojure-api "1.1.10"]
                 [tv-lib "0.1.1-SNAPSHOT"]]
  :ring {:handler tv-api.handler/app}
  :uberjar-name "server.jar"
  :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]]
                   :plugins [[lein-ring "0.10.0"]]}})
