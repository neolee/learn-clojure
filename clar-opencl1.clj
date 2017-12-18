(require '[uncomplicate.commons.core :refer [with-release]])
(require '[uncomplicate.clojurecl
           [core :refer [with-platform platforms with-context context
                         with-queue sort-by-cl-version devices]]
           [legacy :refer [with-default-1 command-queue-1]]])
(require '[uncomplicate.neanderthal
           [core :refer [asum copy dot]]
           [native :refer [dv]]
           [opencl :refer [clv with-default-engine]]])

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
