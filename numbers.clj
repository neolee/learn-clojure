(+ 0x1 2r1 01)

(bit-and 2r1100 2r0100)

(format "0x%x" (bit-and 2r1100 2r0100))

(. Integer toBinaryString 10)
(. Integer toHexString 10)
(. Integer toOctalString 10)
(. Integer toString 10 2)

(. Integer parseInt "A" 16)
(. Integer parseInt "10" 8)
(. Integer parseInt "8")