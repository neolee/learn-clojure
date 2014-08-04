(defn reduce-by [key-fn f init coll]
  (reduce (fn [summaries x]
            (let [k (key-fn x)]
              (assoc summaries k (f (summaries k init) x))))
          {} coll))

(def orders [{:product "Clock", :customer "Wile Coyote", :qty 6, :total 300}
             {:product "Dynamite", :customer "Wile Coyote", :qty 20, :total 5000}
             {:product "Shotgun", :customer "Elmer Fudd", :qty 2, :total 800}
             {:product "Shells", :customer "Elmer Fudd", :qty 4, :total 100}
             {:product "Hole", :customer "Wile Coyote", :qty 1, :total 1000}
             {:product "Anvil", :customer "Elmer Fudd", :qty 2, :total 300}
             {:product "Anvil", :customer "Wile Coyote", :qty 6, :total 900}])

(reduce-by :customer #(+ %1 (:total %2)) 0 orders)
(reduce-by :product #(conj %1 (:customer %2)) #{} orders)

(defn ckey-fn1 [orders]
  [(:customer orders) (:product orders)])
(defn ckey-fn2 [{:keys [customer product]}]
  [customer product])
(def ckey-fn3 (juxt :customer :product))

(reduce-by ckey-fn1 #(+ %1 (:total %2)) 0 orders)

(defn reduce-by-in
  [keys-fn f init coll]
  (reduce (fn [summaries x]
            (let [ks (keys-fn x)]
              (assoc-in summaries ks
                        (f (get-in summaries ks init) x))))
          {} coll))

;; Following two lines give same result
(reduce-by-in ckey-fn3 #(+ %1 (:total %2)) 0 orders)
(reduce #(apply assoc-in %1 %2) {} (reduce-by ckey-fn1 #(+ %1 (:total %2)) 0 orders))

(defn naive-into
  [coll source]
  (reduce conj coll source))

(defn faster-into
  [coll source]
  (persistent! (reduce conj! (transient coll) source)))

(defn transient-capable?
  "Return true if a transient can be obtained for the given collection. i.e. tests if `(transient coll)` will succeed."
  [coll]
  (instance? clojure.lang.IEditableCollection coll))

;; Result not used by assoc! will lost. Use reduce as shown in faster-into to solve that.
(let [tm (transient {})]
  (doseq [x (range 100)]
    (assoc! tm x 0))
  (persistent! tm))

(def a ^{:created (System/currentTimeMillis)} [1 2 3])
(def b (with-meta a (assoc (meta a) :modified (System/currentTimeMillis))))
(def c (vary-meta a assoc :modified (System/currentTimeMillis)))
