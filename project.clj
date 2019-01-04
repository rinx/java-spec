(defproject sdemo "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/spec.alpha "0.1.143"]
                 [cheshire "5.6.3"]
                 [com.google.code.gson/gson "2.8.0"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [org.clojure/test.check "0.10.0-alpha3"]]
  :test-paths ["src/test/clojure"]
  :java-source-paths ["src/main/java" ]
  :profiles {:dev {:repl-options {:port 4555}
                   :dependencies [[org.clojure/tools.namespace "0.3.0-alpha3"]]}})


