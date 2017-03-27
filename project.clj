(defproject sandbox "0.1.0"
  :description "Sandbox for learning the book 'Clojure Programming'"
  :url "http://paradigmx.net/clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.codehaus.jsr166-mirror/jsr166y "1.7.0"]
                 [org.apache.commons/commons-math3 "3.6.1"]
                 [org.clojure/clojure "1.8.0"]
                 [criterium "0.4.4"]
                 [org.clojure/math.combinatorics "0.1.4"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 ]
  :plugins [[lein-clean-m2 "0.1.2"]]
  :jvm-opts ["-Xmx1g"])
