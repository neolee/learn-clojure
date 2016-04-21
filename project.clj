(defproject sandbox "0.1.0"
  :description "Sandbox for learning the book 'Clojure Programming'"
  :url "http://paradigmx.net/clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.codehaus.jsr166-mirror/jsr166y "1.7.0"]
                 [org.apache.commons/commons-math3 "3.2"]
                 [org.clojure/clojure "1.7.0"]
                 [criterium "0.4.3"]
                 [org.clojure/math.combinatorics "0.1.1"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [uncomplicate/clojurecl "0.1.2"]
                 ]
  :jvm-opts ["-Xmx1g"])
