(letfn
    [(foo [x y] #(bar (conj x y) y))
     (bar [x y] (if (> (last x) 10)
                  x
                  #(foo x (+ 2 y))))]
  (trampoline foo [] 1))

;; The trampoline function takes a function f and a variable number of
;; parameters. Trampoline calls f with any parameters that were supplied.
;; If f returns a function, trampoline calls that function with no
;; arguments. This is repeated, until the return value is not a function,
;; and then trampoline returns that non-function value. This is useful
;; for implementing mutually recursive algorithms in a way that won't
;; consume the stack.

(defn foo [x]
  (if (< x 0)
    (println "done")
    #(foo (do (println :x x) (dec x)))))
(trampoline foo 10)

;; Ref: http://pramode.net/clojure/2010/05/08/clojure-trampoline/

(declare f1 f2)

;; Bad implementation which will crash due to SO
(comment
(defn f1 [n]
  (if (= n 0)
    0
    (f2 (dec n))))
(defn f2 [n]
  (if (= n 0)
    0
    (f1 (dec n))))
(f1 10000)
)

;; Solution: make them return fn and use trampoline
(defn f1 [n]
  (if (= n 0)
    0
    #(f2 (dec n))))

(defn f2 [n]
  (if (= n 0)
    0
    #(f1 (dec n))))

(trampoline f1 100000)
