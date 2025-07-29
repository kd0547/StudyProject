### 1. 제네릭 데이터 타입 
제네릭은 여러 구체화된 타입을 사용할 수 있는 함수 시그니처나 구조체 같은 아이템을 정의할 때 사용한다.


#### 1-1. 함수 정의에서 사용하기 

```rust
fn largest_i32(list: &[i32]) -> i32 {
    let mut largest = list[0];
    
    for &item in list.iter() {
        if item > largest {
            largest = item;
        }
    }
    
    largest
}
```

이 함수는 `i32` 전용이라서, `char`, `f64` 또는 사용자 정의 타입 등 다른 타입을 처리하려면 같은 구조의 함수를 반복해서 작성해야 합니다. 이는 코드의 중복과 유지보수성 저하로 이어집니다.

이를 제네릭으로 바꾸면 다음과 같이 다양한 타입에서 재사용 가능한 함수로 바꿀 수 있습니다:

```rust
fn largest<T:PartialOrd + Copy>(list: &[T]) -> T {
    let mut largest = list[0];

    for &item in list.iter() {
        if item > largest {
            largest = item
        }
    }

    largest
}
fn main() {
    let numbers = vec![34, 50, 25, 100, 65];
    let result = largest(&numbers);
    println!("가장 큰 수: {}", result);

    let chars = vec!['y', 'm', 'a', 'q'];
    let result = largest(&chars);
    println!("가장 큰 문자: {}", result);
}
```

#### 1-2. 구조체 정의에서 사용하기 
`Point<T>`는 제네릭 구조체로, `x`와 `y`가 같은 타입 `T`를 갖습니다.

```rust
struct Point<T> {
    x:T,
    y:T,
}

fn main() {
    let integer = Point {x:5,y:10};
    let float = Point {x:1.0,y:4.0};
}

```

##### ❌ 타입이 다른 값을 넣으면?
```rust
fn main() {
    let wont_work = Point { x: 5, y: 4.0 };
}
```
이 코드는 컴파일되지 않습니다.

그 이유는 `Point<T>` 구조체는 x와 y가 동일한 타입이어야 하는데, `5는 i32`,` 4.0은 f64`로 서로 타입이 다르기 때문입니다.

이처럼 타입이 일치하지 않으면 Rust는 제네릭 타입을 하나로 추론할 수 없어 에러가 발생합니다.
만약 서로 다른 타입을 허용하고 싶다면, 제네릭 타입 파라미터를 두 개로 나누어야 합니다

##### 타입이 다르면 어떻게 할까?

타입이 다른 값을 저장하고 싶다면 제네릭 타입 파라미터를 두 개로 나누면 됩니다
```rust
struct Point<T, U> {
    x: T,
    y: U,
}

fn main() {
    let mixed = Point { x: 5, y: 4.0 };
}
```
#### 1-3. 열거자에서 사용하기

```rust
enum Wrapper<T> {
    Value(T),
    Empty,
}

fn main() {
    let a = Wrapper::Value(100);        // Wrapper<i32>
    let b = Wrapper::Value("hello");    // Wrapper<&str>
    let c: Wrapper<f64> = Wrapper::Empty;
}
```
`Wrapper<T>`는 값이 있을 수도 있고 없을 수도 있는 상태를 표현하는 단순한 열거형입니다.

제네릭 타입 `T`를 사용했기 때문에 `i32`, `&str`, `f64` 등 어떤 타입이든 감쌀 수 있습니다.

#### 1-4. 메서드 정의에서 사용하기

제네릭 구조체에 메서드를 정의할 때는 `impl<T>` 블록을 사용합니다.
이렇게 하면 타입 `T`에 관계없이 구조체에 공통적으로 적용되는 메서드를 정의할 수 있습니다.
```rust
struct Point<T> {
    x: T,
    y: T,
}

// 모든 T에 대해 사용할 수 있는 메서드
impl<T> Point<T> {
    fn x(&self) -> &T {
        &self.x
    }
}
```
특정 타입에만 적용되는 메서드를 정의할 수도 있습니다.
예를 들어 `f32` 타입에만 사용할 수 있는 기능이 있다면 다음처럼 작성합니다

```rust
impl Point<f32> {
    fn distance_from_origin(&self) -> f32 {
        (self.x.powi(2) + self.x.powi(2)).sqrt()
    }
}
```
이 `impl Point<f32>` 블록은 `T`가 `f32`일 때만 적용되며, 다른 타입에는 이 메서드가 존재하지 않습니다.
이처럼 특정 타입 전용 메서드를 분리해서 정의하면 타입에 따라 다른 동작을 구현할 수 있습니다.