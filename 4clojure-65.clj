(fn seq-type [coll]
  (let [base (empty coll)]
    (cond
     (= base {})  :map
     (= base #{}) :set
     (= base '()) (if (reversible? coll) :vector :list))))

#(condp = (nth (str %) 0)
   \{ :map
   \c :list
   \[ :vector
   \# :set)
