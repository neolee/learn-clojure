module Main where
import System.Environment(getArgs)
import qualified Data.Map as Map
 
primes :: [Int]
primes =
  2 : (nextPrime Map.empty 3)
  where
    nextPrime :: Map.Map Int Int -> Int -> [Int]
    nextPrime sieve candidate =
      case (Map.lookup candidate sieve) of
        Nothing  -> candidate : (nextPrime (nextSieve sieve candidate) $ candidate + 2)
        Just _ -> nextPrime (nextSieve sieve candidate) $ candidate + 2
 
    nextSieve :: Map.Map Int Int -> Int -> Map.Map Int Int
    nextSieve sieve candidate =
      case (Map.lookup candidate sieve) of
        Just v -> enqueue (Map.delete candidate sieve) candidate v
        Nothing    -> enqueue sieve candidate $ candidate + candidate
 
    enqueue :: Map.Map Int Int -> Int -> Int -> Map.Map Int Int
    enqueue sieve n step =
      let m = n + step
      in case (Map.lookup m sieve) of
        Just _ -> enqueue sieve m step
        Nothing -> Map.insert m step sieve
 
main = do
  n <- getArgs
  let limit = (read (head n)) :: Int
  putStrLn $ show . last . take limit $ primes