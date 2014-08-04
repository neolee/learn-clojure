;; Our coordinate system: y=row(top-down), x=column(lefy-right)

(defn empty-board
  "Creates a rectangular empty board of the specified width and height."
  [w h]
  (vec (repeat w (vec (repeat h nil)))))

(defn populate
  "Turns :on each of the cells specified as [y x] coordinates."
  [board living-celles]
  (reduce (fn [board coordinates]
            (assoc-in board coordinates :on))
          board
          living-celles))

;; Define a glider board
(def init-cells #{[2 0] [2 1] [2 2] [1 2] [0 1]})
(def glider (populate (empty-board 6 6) init-cells))

;; The 'traditional' implementations and helpers of the game rules: indexed-step
(defn neighbours
  [[x y]]
  (for [dx [-1 0 1] dy [-1 0 1] :when (not= 0 dx dy)]
    [(+ dx x) (+ dy y)]))

(defn count-neighbours
  [board loc]
  (count (filter #(get-in board %) (neighbours loc))))

(defn indexed-step
  "Yields the next state of the board, using indices to determine neighbours, liveness, etc."
  [board]
  (let [w (count board)
        h (count (first board))]
    (loop [new-board board x 0 y 0]
      (cond (>= x w) new-board
            (>= y h) (recur new-board (inc x) 0)
            :else (let [new-liveness (case (count-neighbours board [x y])
                                       2 (get-in board [x y])
                                       3 :on
                                       nil)]
                    (recur (assoc-in new-board [x y] new-liveness) x (inc y)))))))

;; Some modification to use reduce instead of explicit looping
;; Firstly replace loop with reduce
(defn indexed-step2
  [board]
  (let [w (count board)
        h (count (first board))]
    (reduce
     (fn [new-board x]
       (reduce
        (fn [new-board y]
          (let [new-liveness (case (count-neighbours board [x y])
                               2 (get-in board [x y])
                               3 :on
                               nil)]
            (assoc-in new-board [x y] new-liveness)))
        new-board (range h)))
     board (range w))))
; ...and collapse nested reduces into one
(defn indexed-step3
  [board]
  (let [w (count board)
        h (count (first board))]
    (reduce
     (fn [new-board [x y]]
       (let [new-liveness (case (count-neighbours board [x y])
                            2 (get-in board [x y])
                            3 :on
                            nil)]
         (assoc-in new-board [x y] new-liveness)))
     board (for [x (range h) y (range w)] [x y]))))

;; Now try to completely remove indices
(defn window
  "Return a lazy sequence of 3-item windows centered around items of coll, padded as neccesary with pad or nil."
  ([coll] (window nil coll))
  ([pad coll]
     (partition 3 1 (concat [pad] coll [pad]))))

(defn cell-block
  "Creates a sequence of 3x3 windows from a triple of 3 sequences."
  [[left mid right]]
  (window (map vector left mid right)))

(defn liveness
  "Returns the liveness (nil or :on) of the center cell for the next step."
  [block]
  (let [[_ [_ center _] _] block]
    (case (- (count (filter #{:on} (apply concat block)))
             (if (= :on center) 1 0))
      2 center
      3 :on
      nil)))

(defn- step-row
  "Yields the next state of the center row."
  [rows-triple]
  (vec (map liveness (cell-block rows-triple))))

(defn index-free-step
  "Yields the next state of the board."
  [board]
  (vec (map step-row (window (repeat nil) board))))

;; Finally a very simple and elegant solution based on more deep analysis of the game rules
(defn step
  "Yield the next step of the world."
  [cells]
  (set (for [[loc n] (frequencies (mapcat neighbours cells))
              :when (or (= n 3) (and (= n 2) (cells loc)))]
         loc)))

;; Now let's make this abstraction further to define a HOF for any similar rules
(defn stepper
  "Returns a step function for Life-like cell automata. neighbour takes a location and returns a sequential collection of locations. survive? and birth? are predicates on the number of living neighbours."
  [neighbours birth? survive?]
  (fn [cells]
    (set (for [[loc n] (frequencies (mapcat neighbours cells))
               :when (if (cells loc) (survive? n) (birth? n))]
           loc))))

(defn hex-neighbours
  [[x y]]
  (for [dx [-1 0 1] dy (if (zero? dx) [-2 2] [-1 1])]
    [(+ dx x) (+ dy y)]))

(def hex-step (stepper hex-neighbours #{2} #{3 4}))

(hex-step #{[0 0] [1 1] [1 3] [0 4]})
