; Ref: http://clojure.roboloco.net/?p=613
(ns euler.p070)

(def primes
  "Lazy sequence of all the prime numbers."
  (concat
   [2 3 5 7]
   (lazy-seq
    (let [primes-from
          (fn primes-from [n [f & r]]
            (if (some #(zero? (rem n %))
                      (take-while #(<= (* % %) n) primes))
              (recur (+ n f) r)
              (lazy-seq (cons n (primes-from (+ n f) r)))))
          wheel (cycle [2 4 2 4 6 2 6 4 2 4 6 6 2  6 4  2
                        6 4 6 8 4 2 4 2 4 8 6 4 6  2 4  6
                        2 6 6 4 2 4 6 2 6 4 2 4 2 10 2 10])]
      (primes-from 11 wheel)))))

(defn prime? [n]
  (and (< 1 n)
       (not-any? #(zero? (rem n %)) (take-while #(<= (* % %) n) primes))))

(def candidates
  (let [center 3162] ;; Rough estimate of sqrt(1e7)
    (interleave (take-while #(> % 1) (filter prime? (iterate dec center)))
                (take-while #(> (* 2 center) %)
                            (filter prime? (iterate inc center))))))

(defn lazy-combinations
  "Returns lazy combinations of the list, possibly infinitely long."
  ([as] (lazy-combinations [(first as)] (rest as)))
  ([as bs]
     (let [b (first bs)]
       (lazy-cat (map #(vector % b) as)
                 (lazy-combinations (conj as b) (rest bs))))))

(defn totient [a b]
  (* a b (reduce * (map #(- 1 (/ 1 %)) [a b]))))

(defn are-perms? [a b] (= (sort (str a)) (sort (str b))))

(defn euler-70-works []
  (->> (lazy-combinations candidates)
       (filter (fn [[a b]] (and (< (* a b) 1e7)
                                (are-perms? (* a b) (totient a b)))))
       (take 50) ;; arbitrary, but it works
       (map (fn [[a b]] [(* a b) (/ (* a b) (totient a b))]))
       (sort-by second)
       first
       first))

(time (euler-70-works))
