(require '[uncomplicate.commons.core :refer [with-release]])
(require '[uncomplicate.clojurecl.core :refer [with-default]])
(require '[uncomplicate.neanderthal
           [core :refer [asum]]
           [opencl :refer [clv with-default-engine]]])

(with-default
  (with-default-engine
    (with-release [gpu-x (clv 1 -2 5)]
      (asum gpu-x))))
