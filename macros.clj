(defmacro defvar
  [name value docstring]
  `(def ~(with-meta name {:doc docstring}) ~value))

(defvar var1 "value1" "a doc string")

(comment
  (defmacro defvar
    [name value docstring]
    (list 'def (with-meta name {:doc docstring}) value))
  
  (defvar var2 "value2" "a doc string")
  )

(comment
  (macroexpand-1 `(def (with-meta name {:doc docstring}) value))
  (macroexpand-1 (list 'def (with-meta name {:doc docstring}) value))
  )

(defmacro simple-apply
  [f args]
  `(~f ~@args)
  )

(comment
  (simple-apply + [1 2 3])
  )

; ~@ takes the following thing unquotes it and "removes" the parentheses, which means "thing"
; should be a seq. Using the above definition (simple-apply + [1 2 3]) expands to (+ 1 2 3).
; Would we have used the ~, the macro would expand to (+ [1 2 3]).

(defmacro simple-apply2
  [f args]
  `(~f ~args)
  )

(comment
  (simple-apply2 + [1 2 3]) ; => (+ [1 2 3]) and throw an error
  )

; A caveat of macros however is the source of its power: the arguments are not evaluated! So compare the following example.

(defn set-title-and-show
  [frame title]
  (.setTitle frame title)
  (.setVisible frame true)
  frame)

(comment
  (defmacro set-title-and-show
    [frame title]
    `(do
       (.setTitle ~frame ~title)
       (.setVisible ~frame true)
       ~frame))
  )

(comment
  (set-title-and-show (new javax.swing.JFrame) "Hello")
  )

; Simplest code-as-data

(let [x 1] 
  (inc x))

; A entry level sample for explaining the basic code-as-data concept

(defmacro deftag [name]
    (list 'defn name ['& 'inner]
          (list 'str "<" (str name) ">"
                '(apply str inner)
                "</" (str name) ">")))
                
(deftag html)
(deftag body)
(deftag h1)
(deftag p)
; 'user/p

(p "Hello world!")
; "<p>Hello world!</p>"

(macroexpand-1 '(deftag blink))
; returns:
(clojure.core/defn blink [& inner]
  (clojure.core/str "<" "blink" ">"
                    (clojure.core/apply clojure.core/str inner)
                    "</" "blink" ">"))
