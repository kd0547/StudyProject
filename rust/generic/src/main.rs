//1-1. 함수에서 제네릭 타입 사용
fn largest<T:PartialOrd + Copy>(list: &[T]) -> T {
    let mut largest = list[0];

    for &item in list.iter() {
        if item > largest {
            largest = item
        }
    }

    largest
}
//1-2. 구조체 정의에서 사용하기
struct Point<T> {
    x:T,
    y:T,
}

//1-3. 열거자에서 사용하기
enum Wrapper<T> {
    Value(T),
    Empty,
}

//1-4. 메서드에서 사용하기
impl<T> Point<T> {
    fn x(&self) -> &T {
        &self.x
    }
}
impl Point<f32> {
    fn distance_from_origin(&self) -> f32 {
        (self.x.powi(2) + self.x.powi(2)).sqrt()
    }
}

fn main() {
    //1-1
    let numbers = vec![34, 50, 25, 100, 65];
    let result = largest(&numbers);
    println!("가장 큰 수: {}", result);

    let chars = vec!['y', 'm', 'a', 'q'];
    let result = largest(&chars);
    println!("가장 큰 문자: {}", result);

    //1-2
    let integer = Point {x:5,y:10};
    let float = Point {x:1.0,y:4.0};

    //1-3
    // Wrapper<i32>
    let a = Wrapper::Value(100);
    // Wrapper<&str>
    let b = Wrapper::Value("hello");
    let c: Wrapper<f64> = Wrapper::Empty;
}
