; Blog: http://dragan.rocks/articles/17/Clojure-Linear-Algebra-Refresher-Vector-Spaces
(require '[uncomplicate.commons.core :refer :all])
(require '[uncomplicate.neanderthal
           [core :refer :all]
           [linalg :refer :all]
           [native :refer :all]
           [opencl :refer :all]])
(require '[uncomplicate.clojurecl
           [core :refer :all]
           [legacy :refer [with-default-1 command-queue-1]]])

; Vectors, addition and scalar multiplication
(def v1 (dv -1 2 5.2 0))
(def v2 (dv (range 22)))
(def v3 (dv -2 -3 1 0))
(xpy v1 v3)
(scal 2.5 v1)
(axpy! 2.5 v1 v3)

(dv 7)
(zero v2)
(def v4 (scal -1 v1))
(axpby! 1 v1 -1 v3)

; Linear combinations
(let [u (dv 2 5 -3)
      v (dv -4 1 9)
      w (dv 4 0 2)]
  (axpy 2 u -3 v 1 w))

; Dot product
(let [u (dv 1 -2 4)
      v (dv 3 0 2)]
  (dot u v))
; Norm
(nrm2 (dv 1 3 5))
; Construction of unit vector
(let [v (dv 1 3 5)]
  (scal (/ (nrm2 v)) v))
; Angle
(let [u (dv 1 0 0)
      v (dv 1 0 1)]
  (/ (dot u v) (nrm2 u) (nrm2 v)))
; Because cos(pi/4)=0...(i.e orthogonal)
(dot (dv 2 -3 1) (dv 1 2 4))
; Distance
(let [x (dv 4 0 -3 5)
      y (dv 1 -2 3 0)]
  (nrm2 (axpy -1 y x)))

; Matrix
(def m (dge 2 3 (range 6)))
(nrm2 m)

; Linear dependence
(let [x (dv 2 -1 3)
      y (dv 4 -2 6)]
  (/ (dot x y) (nrm2 x) (nrm2 y)))

; Solving systems of linear equations
(let [cs (dge 3 1)]
  (sv! (dge 3 3 [1 2 0
                 0 1 -1
                 1 1 2])
       cs)
  cs)

; Rank
(let [a (dge 4 4 [1 0 0 0
                  2 0 0 0
                  0 1 0 0
                  0 0 1 0])]
  (svd a))

(let [a (dge 4 4 [1 0 0 0
                  2 0 0 0
                  0 1 0 0
                  0 0 1 0])
      s (dgd 4)
      superb (dgd 4)]
  (svd! a s superb)
  s)

; Orthonormal vectors and projections
(let [v (dv 6 7)
      u (dv 1 4)]
  (scal (/ (dot u v) (dot u u)) u))

; Hello GPU (through OpenCL 1.2)
(with-default-1
  (with-default-engine
    (with-release [gpu-x (clv (range 100000))
                   gpu-y (copy gpu-x)]
      (dot gpu-x gpu-y))))
; Native method as comparison
(let [x (dv (range 100000))
      y (copy x)]
  (dot x y))
; More thorough configuraion
(with-platform (first (platforms))
  (let [dev (first (sort-by-cl-version (devices :gpu)))]
    (with-context (context [dev])
      (with-queue (command-queue-1 dev)
        (with-default-engine
          (with-release [gpu-x (clv 1 -2 5)]
            (asum gpu-x)))))))
