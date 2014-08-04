(use 'clojure.math.numeric-tower)
(import '(org.apache.commons.math3.special Gamma))

(defn fib-seq
  ([] (fib-seq 1 1))
  ([a b] (cons a (lazy-seq (fib-seq b (+ b a))))))

(def fib-seq_recur
     (lazy-cat [0 1] (map + (rest fib-seq_recur) fib-seq_recur)))

(def s1 (sqrt 5.0))
(def s2 (* a 0.5))
(defn fib [n]
  (int (/ (- (expt (+ 0.5 s2) n) (expt (- 0.5 s2) n)) s1)))

(defn fac [n]
  (floor (Math/exp (. Gamma logGamma (inc n)))))

(defn fac [n]
           (loop [n n r 1]
                (if (= n 0)
                    r
                    (recur (dec n) (*' n r)))))

(def fac (memoize fac))
