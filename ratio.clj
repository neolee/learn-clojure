(def a (/ 1.0 3.0))
(def b (/ 3.0 1.0))
(def c (* a a a a a a a a a a))
(def d (* b b b b b b b b b b))

(def a1 (/ 1 3))
(def b1 (/ 3 1))
(def c1 (* a1 a1 a1 a1 a1 a1 a1 a1 a1 a1))
(def d1 (* b1 b1 b1 b1 b1 b1 b1 b1 b1 b1))

(comment
  (* a b)
  (* c d)
  (* a1 b1)
  (* c1 d1)
  )
