package fava.data;

import fava.Currying.F1;
import fava.Currying.F2;

public class Maybe<T> {
  private boolean hasValue;
  private T value;

  private Maybe(boolean hasValue, T value) {
    this.hasValue = hasValue;
    this.value = value;
  }

  public static <T, R> F1<Maybe<T>, Maybe<R>> fmap(final F1<T, R> f) {
    return new F1<Maybe<T>, Maybe<R>>() {
      @Override
      public Maybe<R> apply(Maybe<T> maybeT) {
        return maybeT.hasValue ? just(f.apply(maybeT.value)) : Maybe.<R>nothing();
      }
    };
  }

  public static <T1, T2, R> F2<Maybe<T1>, Maybe<T2>, Maybe<R>> liftA(final F2<T1, T2, R> f) {
    return new F2<Maybe<T1>, Maybe<T2>, Maybe<R>>() {
      @Override public Maybe<R> apply(Maybe<T1> arg1, Maybe<T2> arg2) {
        return (arg1.hasValue && arg2.hasValue) ? just(f.apply(arg1.value, arg2.value)) : Maybe.<R>nothing();
      }
    };
  }

  public static <T> Maybe<T> just(T t) {
    return new Maybe<T>(true, t);
  }

  public static <T> Maybe<T> nothing() {
    return new Maybe<T>(false, null);
  }

  public boolean hasValue() {
    return hasValue;
  }

  public T getValue() {
    assert hasValue;
    return value;
  }

  public boolean equals(Maybe<T> rhs) {
    return (rhs != null && hasValue == rhs.hasValue) && (!hasValue || value.equals(rhs.value));
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Maybe<?>) ? equals((Maybe<T>) obj) : false;
  }
}