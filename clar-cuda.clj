(require '[uncomplicate.commons.core :refer [with-release]])
(require '[uncomplicate.neanderthal
           [core :refer [asum]]
           [cuda :refer [cuv with-default-engine]]])

(with-default-engine
  (with-release [gpu-x (cuv 1 -2 5)]
    (asum gpu-x)))
