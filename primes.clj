(ns paradigmx.math)

(defn primes []
  (letfn [(enqueue [sieve n step]
            (let [m (+ n step)]
              (if (sieve m)
                (recur sieve m step)
                (assoc sieve m step))))
          (next-sieve [sieve candidate]
            (if-let [step (sieve candidate)]
              (-> sieve
                  (dissoc candidate)
                  (enqueue candidate step))
              (enqueue sieve candidate (+ candidate candidate))))
          (next-primes [sieve candidate]
            (if (sieve candidate)
              (recur (next-sieve sieve candidate) (+ candidate 2))
              (cons candidate
                    (lazy-seq (next-primes (next-sieve sieve candidate)
                                           (+ candidate 2))))))]
    (cons 2 (lazy-seq (next-primes {} 3)))))

(fn [n]
  (->>
   (range)
   (drop 2)
   (filter (fn [x] (every? #(< 0 (mod x %)) (range 2 x))))
   (take n)))

(fn primes [x]
  (if (= x 1)
    [2]
    (let [pp (primes (- x 1))]
      (conj pp
            (first (filter
                    (fn [i] (every? #(< 0 (mod i %)) pp))
                    (range (last pp) (* 2 (last pp)))))))))

(fn [n]
  (take n(filter
          (fn is-prime [n]
            (nil?
             (some
              #(zero? (mod n %))
              (range 2 n))))
          (range 2 1000))))
