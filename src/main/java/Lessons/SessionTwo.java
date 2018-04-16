package Lessons;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;

import java.util.Arrays;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

public class SessionTwo {
    public SessionTwo() {

    }

    public Observable<String> returnObservable(List<String> myStrings) {
        return Observable.fromIterable(myStrings);
    }

    public void sampleForObserverObject() {
        List<String> myStrings = Arrays.asList("Manav", "Disha", "Jidnyasa", "Kalpesh", "Sarthak", "Gunjan");

        Observable<String> source = returnObservable(myStrings);
        io.reactivex.Observer<Integer> myObserver = new io.reactivex.Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Done!!!");
            }
        };

        source.map(String::length).filter(e -> e >= 5)
                .subscribe(myObserver);
    }

    public void sampleLambdaObserver() {
        List<String> myStrings = Arrays.asList("Manav", "Disha", "Priyanka", "Shank", "Rahul", "Rishi", "Richa");
        Observable<String> source = Observable.fromIterable(myStrings);

        Consumer<Integer> onNext = e -> System.out.println(e);
        Consumer<Throwable> onError = Throwable::printStackTrace;
        Action onComplete = () -> System.out.println("Done !!");
        source.map(e -> e.length()).filter(i -> i >= 5)
                //Allowed types of lambdas for observer interfaces.
                //.subscribe(onNext, onError, onComplete);
                //.subscribe(onNext, onError);
                .subscribe(onNext, onError);

    }

    public void coldObservableExample() {
        List<String> myStrings = Arrays.asList("Manav", "Disha");
        Observable<String> source = Observable.fromIterable(myStrings);

        source.subscribe(e -> System.out.println(e), Throwable::printStackTrace, () -> System.out.println("Done !!"));
        source.map(e -> e.length())
                .subscribe(e -> System.out.println(e), Throwable::printStackTrace, () -> System.out.println("Done !!"));
    }

    public void hotObservableExample() {
        List<String> myStrings = Arrays.asList("Manav", "Dharmendra");
        Observable<String> source = Observable.fromIterable(myStrings);
        ConnectableObservable<String> myHotSource = source.publish();

        myHotSource.subscribe(e -> System.out.println(e));

        myHotSource.map(String::length).subscribe(e -> System.out.println(e));

        myHotSource.connect();
        //This wont be printed.
        myHotSource.subscribe(e -> System.out.println(e));

    }

    public void rangeObservable() {
        Observable<Integer> source = Observable.range(1,10);
        source.subscribe(e -> System.out.println(e), Throwable::printStackTrace, () -> System.out.println("Done !!"));
        //Have a good look at the arguements
        Observable.rangeLong(5,10)
                .subscribe(e -> System.out.println(e));
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void trickyColdIntervalObservable() {
        Observable<Long> seconds = Observable.interval(1, TimeUnit.SECONDS);
        seconds.subscribe(s -> System.out.println(s + " - Okay 1"));
        sleep(5000);
        seconds.subscribe(s -> System.out.println(s + " - Okay 2"));
        sleep(5000);
    }

    public void trickyHotIntervalObservable() {
        ConnectableObservable<Long> seconds = Observable.interval(1, TimeUnit.SECONDS).publish();
        seconds.subscribe(s -> System.out.println(s + " - Okay 1"));
        seconds.connect();
        sleep(5000);
        seconds.subscribe(s -> System.out.println(s + " - Okay 2"));
        sleep(5000);
    }

    public void intervalObservable() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(s -> System.out.println(s + " - Okay "));

        sleep(5000);
    }

    public void emptyObservable() {
        Observable<String> emptyObservable = Observable.empty();
        emptyObservable.subscribe(s -> System.out.println(s), Throwable::printStackTrace, ()->System.out.println("Done !!"));
    }

}
