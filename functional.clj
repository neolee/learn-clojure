;; Anonymous Functions

(comment
  ((fn [x] (* x x)) 3)
  (map #(list %1 (inc %2)) [1 2 3] [1 2 3])
  (map (fn [x y] (list x (inc y))) [1 2 3] [1 2 3])
  (map #(list % (inc %)) [1 2 3])
  (map (fn [x] (list x (inc x))) [1 2 3])
  (#(apply str %&) "Hello")
  (#(apply str %&) "Hello" ", " "World!")
  )

;; Lazy Evaluation of Sequences

(defn free-mem [] (.. Runtime (getRuntime) (freeMemory)))
(defn big-computation [x] (. Thread sleep 1000) (* 10 x))

(comment
  (time (big-computation 1))
  )

(time (def nums (range 1000000000)))

; The comments below should be read in the numbered order
; to better understand this code.
(comment
  (time				        ; [7] time the transaction
   (def v				; [6] save vector as v
	(apply vector		        ; [5] turn the list into a vector
	       (map big-computation     ; [4] process each item for 1 second
		    (take 5	        ; [3] take first 5 from filtered items
			  (filter	; [2] filter items 10000 to 10010
			   (fn [x] (and (> x 10000) (< x 10010)))
			   nums))))))	; [1] nums = 1 billion items
  )

(comment
  (free-mem)
  (time (def v (apply vector (map big-computation (take 5 (filter (fn [x] (and (> x 10000) (< x 10010))) nums))))))
  (free-mem)
  (time (seq v))
  )

(time (def comps (map big-computation nums)))
(defn t5 [] (take 5 comps))

(comment
  (time (doall (t5)))
  (time (doall (t5)))
  )

;; Infinite Data Source

(def nums (iterate inc 0))

(comment
  (take 5 nums)
  (take 10 nums)

  (iterate inc 0) ; Infinite iteration, which will give out an out-of-heap error
)

;; List Comprehension

(def nums (iterate inc 0))
(def s1 (for [x nums :when (zero? (rem x 4))] (inc x)))
(def s2 (map inc (filter (fn [x] (zero? (rem x 4))) nums)))

(comment
  (take 5 s1)
  (take 5 s2)
  )

; High order function

(map inc [1 2 3 4 5])
; (2 3 4 5 6)

(map #(* 5 %) [1 2 3 4 5])
; (5 10 15 20 25)

(def quintuple (partial * 5))
; #'user/quintuple
(map quintuple [1 2 3 4 5])
; (5 10 15 20 25)

; Lazy evaluation

(time (def nums (range 1000000000)))
; "Elapsed time: 0.059 msecs"
; #'user/nums

(defn big-computation [x] (. Thread sleep 1000) (* 10 x))
; #'user/big-computation

(time (def comps (map big-computation nums)))
; "Elapsed time: 0.053 msecs"
; #'user/comps

(defn t5 [] (take 5 comps))
; #'user/t5
(time (doall (t5)))
; "Elapsed time: 32024.377 msecs"
; (0 10 20 30 40)
(time (doall (t5)))
; "Elapsed time: 0.046 msecs"
; (0 10 20 30 40)
