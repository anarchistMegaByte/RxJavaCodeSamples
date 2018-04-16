package Lessons;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SessionOne {
    public SessionOne() {

    }
    //Map operator example
    public Observable<String> getObservableJust() {
        return Observable.just("One","Two","Three","Four");
    }

    public Observable<Integer> attachMapOperator(Observable<String> myString) {
        return myString.map(s -> s.length());
    }

    public Disposable attachSubscriber(Observable<Integer> observable) {
        return observable.subscribe(s -> System.out.print(s + "\n"));
    }

    public void allinOne() {
        Observable<String> myStrings = Observable.just("One","Two","Three","Fourth");
        myStrings.map(s-> s.length()).subscribe(s -> System.out.println(s));
    }

    //Interval operator Example
    public Observable<Long> getObservableInterval() {
        return Observable.interval(1, TimeUnit.SECONDS);
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Create operator example
    public void implementingCreateOperator() {
        Observable<String> source = Observable.create(emitter -> {
                try {
                    emitter.onNext("Manav");
                    emitter.onNext("Disha");
                    emitter.onNext("Ashok");
                    emitter.onNext("Suvarna");
                    emitter.onComplete();
                } catch (Throwable e) {
                    emitter.onError(e);
                }
            }
        );

        source.subscribe(s -> System.out.println(s), Throwable::printStackTrace);
    }

    //Filter and map operator example
    public void getObservableFilterAndMap() {
        Observable<String> source = Observable.create(emitter -> {
                    try {
                        emitter.onNext("Manav");
                        emitter.onNext("Disha");
                        emitter.onNext("Ashok");
                        emitter.onNext("Suvarna");
                        emitter.onComplete();
                    } catch (Throwable e) {
                        emitter.onError(e);
                    }
                }
        );
//        Observable<Integer> myInt = source.map(String::length);
//        Observable<Integer> myFilterInt = myInt.filter(i -> i >= 5);
//        myFilterInt.subscribe(s -> System.out.println(s));

        //Chaining them all
        source.map(String::length)
                .filter(i -> i >=5)
                .subscribe(s -> System.out.println(s));

    }

    //FromIterable example
    public void getObservableFromIterable() {
        List<String> myStr = Arrays.asList("Manav", "Dharmendra", "Ashish");
        Observable<String> myString = Observable.fromIterable(myStr);

        myString.map(s -> s.length()).filter(i -> i > 6).subscribe(s -> System.out.println(s));
    }


    public static void SummerizingSessionOne() {
        SessionOne one = new SessionOne();

        System.out.print("MapOperator Example : ");
        Observable<String> myStringsObservable =  one.getObservableJust();
        Observable<Integer> myStringInInt = one.attachMapOperator(myStringsObservable);
        Disposable myDisposable = one.attachSubscriber(myStringInInt);
        System.out.print("All in One : ");
        one.allinOne();

        System.out.println();

        System.out.println("This Works as a handler in android. ");
        System.out.print("IntervalOperator Example : ");
        Observable<Long> eventBasedObservable = one.getObservableInterval();
        eventBasedObservable.subscribe(s -> System.out.println(s));
        one.sleep(5500);

        System.out.println();

        System.out.print("Create Operator Example : ");
        one.implementingCreateOperator();

        System.out.println();

        System.out.print("Map And filter Operator Example : ");
        one.getObservableFilterAndMap();

        System.out.println();

        System.out.print("FromIterable Operator Example : ");
        one.getObservableFromIterable();
    }
}
