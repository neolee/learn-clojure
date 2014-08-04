{-# OPTIONS_GHC -O2 #-}   --   unbounded merging idea due to Richard Bird
module Main where         --   double staged production idea due to Melissa O'Neill
                          --   tree folding idea Dave Bayer / simplified formulation Will Ness
main = do  s <- getLine   
           print $ take 5 $ drop (read s-5) primes  -- logBase (n2/n1) (t2/t1): O(n^1.20)
 
primes :: [Int]           -- first corecursive algorithm in known human history:
primes = 2 : g (fix g)    --  creates its argument while analyzing its output
  where
    g xs = 3 : gaps 5 (unionAll [[x*x, x*x+2*x..] | x <- xs])
  
fix g = xs where xs = g xs                       -- global defn to avoid space leak
gaps k s@(x:xs) | k<x  = k:gaps (k+2) s          -- (== [k,k+2..]`minus`s  ,k<=x
                | True =   gaps (k+2) xs         --      (-fno-full-laziness : no leak) )
 
unionAll ((x:xs):t) = x : union xs (unionAll (pairs t))
  where 
    pairs ((x:xs):ys:t) = (x : union xs ys) : pairs t
    union a@(x:xs) b@(y:ys) = case compare x y of
         LT -> x : union  xs b
         EQ -> x : union  xs ys
         GT -> y : union  a  ys
    union a        b        = if null a then b else a