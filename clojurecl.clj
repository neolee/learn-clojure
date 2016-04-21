(ns clojurecl
  (:use [uncomplicate.clojurecl core info]))

(map info (platforms))
(map info (devices (first (platforms))))
