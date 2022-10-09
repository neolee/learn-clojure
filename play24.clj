(require '[clojure.math.combinatorics :as c]
         '[clojure.walk :as w])

(def ^:private op-maps
  (map #(zipmap [:o1 :o2 :o3] %) (c/selections '(* + - /) 3)))

(def ^:private patterns '((:o1 (:o2 :n1 :n2) (:o3 :n3 :n4))
                          (:o1 :n1 (:o2 :n2 (:o3 :n3 :n4)))
                          (:o1 (:o2 (:o3 :n1 :n2) :n3) :n4)))

(defn- eval-safe [sexp]
  (try
    (= (eval sexp) 24)
    (catch ArithmeticException _e false)))

(defn play24 [& digits]
  (->> (for [om op-maps dm (->> digits sort c/permutations
                                (map #(zipmap [:n1 :n2 :n3 :n4] %)))]
         (w/prewalk-replace dm (w/prewalk-replace om patterns)))
       (apply concat)
       (filter eval-safe)
       (map println)
       (doall)
       (count)
       (str "total: ")
       (println)))
