(fn gcd [a b]
  (if (zero? b)
    a
    (gcd b (mod a b))))

;; The mod function is defined as the amount by which
;; a number exceeds the largest integer multiple of the
;; divisor that is not greater than that number.
;; The largest integer multiple of 5 not greater than -2
;; is 5 * -1 = -5. The amount by which -2 exceeds -5 is 3.
(mod -2  5) ; => 3

;; rem and mod are commonly used to get the remainder.
;; mod means Gaussian mod, so the result is always
;; non-negative.  Don't confuse it with ANSI C's %
;; operator, which despite being although pronounced
;; 'mod' actually implements rem, i.e. -10 % 3 = -1.
(mod -10 3) ; => 2
(rem -10 3) ; => -1
