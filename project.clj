(defproject sandbox "0.1.0"
  :description "Sandbox for learning the book 'Clojure Programming'"
  :url "http://paradigmx.net/clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.logging "1.1.0"]
                 [ch.qos.logback/logback-classic "1.3.0-alpha5"]
                 [org.apache.commons/commons-math3 "3.6.1"]
                 [uncomplicate/neanderthal "0.40.0" :exclusions [org.jcuda/jcuda-natives
                                                                 org.jcuda/jcublas-natives]]
                 [org.clojure/math.combinatorics "0.1.6"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 ]
  :resource-paths ["resources"])
