;; Sequence Functions

(comment
  (first (list 1 2 3 4))
  (first (list))
  (first nil)
  (first (drop 3 (list 1 2 3 4)))
  )

(comment
  (rest (list 1 2 3 4))
  (rest (list))
  (rest nil)
  (rest (take 3 (list 1 2 3 4)))
  )

(comment
  (map (fn [x] true) (range 10))
  (map (constantly true) (range 10)) 
  (map (fn [x] (* 2 x)) (range 10))
  (map (partial * 2) (range 10))
  (map (fn [a b] (* a b)) (range 10) (range 10))
  (map * (range 10) (range 10))
  (map (fn [a b] (* a b)) (range 10) (range 10) (range 10))
  (map * (range 10) (range 10) (range 10))
  (map * (replicate 5 2) (range 5))
  (map * (replicate 10 2) (range 5))
  (map * (repeat 2) (range 5))
  )

(comment
  (every? (fn [x] true) (range 10))
  (every? (constantly true) (range 10))
  (every? pos? (range 10))
  (every? pos? (range 1 10))
  (every? (fn [x] (== 0 (rem x 2))) (range 1 10))
  (every? (fn [x] (== 0 (rem x 2))) (range 1 10 2))
  (every? (fn [x] (== 0 (rem x 2))) (range 2 10 2))
  (every? even? (range 1 10 2))
  (every? even? (range 2 10 2))
  (not-every? pos? (range 10))
  (not-every? pos? (range 1 10))
  )

;; Looping and Iterating

(comment
  ;; Version 1
  (loop [i 1]
    (when (< i 20)
      (println i)
      (recur (+ 2 i))))
 
  ;; Version 2
  (dorun (for [i (range 1 20 2)]
	   (println i)))
 
  ;; Version 3
  (doseq i (range 1 20 2)
    (println i))
  )

;; Mutual Recursion

; forward declaration
(def my_even1?)
 
; define odd in terms of 0 or even
(defn my_odd1? [n]
  (if (zero? n)
      false
      (my_even1? (dec n))))
 
;define even? in terms of 0 or odd
(defn my_even1? [n]
  (if (zero? n)
      true
      (my_odd1? (dec n))))

(comment
  (my_even1? 3)
  (my_even1? 8)
  )

; Mutual recursion is not possible in internal functions defined with let. To declare
; a set of private recursive functions, you can use the above technique with defn-
; instead of defn, which will generate private definitions.
; However one can emulate mutual recursive functions with loop and recur.

(use 'clojure.contrib.fcase)
 
(defmacro multi-loop
  [vars & clauses]
  (let [loop-var  (gensym "multiloop__")
        kickstart (first clauses)
        loop-vars (into [loop-var kickstart] vars)]
    `(loop ~loop-vars
       (case ~loop-var
          ~@clauses))))
 
(defn my_even2?
  [n]
  (multi-loop [n n]
    :even (if (zero? n)
            true
            (recur :odd (dec n)))
    :odd  (if (zero? n)
            false
            (recur :even (dec n)))))

(comment
  (my_even2? 3)
  (my_even2? 8)
  )