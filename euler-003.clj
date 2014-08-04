(ns euler.p003
  (require [clojure.core.reducers :as r]))

(declare largest-prime-factor-for)
(declare factors-of)
(declare source-factors)
(declare source-naturals)
(declare factor?)
(declare prime?)
(declare certainty)

(defn answer []
  (time (largest-prime-factor-for 600851475143)))

(defn largest-prime-factor-for [n]
  (let [prime-factors (r/filter prime?
                                (factors-of n))]
    (last (into [] prime-factors))))

(defn factors-of [n]
  (r/filter #(factor? n %)
            (source-factors n)))

(defn source-factors [n]
  (r/take-while #(< % (int (Math/sqrt n)))
                (source-naturals)))

(defn source-naturals []
  (r/map #(+ % 2) (range)))

(defn factor? [n possib]
  (zero? (mod n possib)))

(defn prime? [n]
  (.isProbablePrime (BigInteger/valueOf n) certainty))

(def certainty 10)