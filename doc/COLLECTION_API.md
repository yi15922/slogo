# API Lab Discussion
# Collections API Discussion

## Names and NetIDs
* Patrick Liu (pyl8)
* Kenneth Moore III (km460)
* Yi Chen (yc311)
* Liam Idrovo (lai3)

### What is the purpose of each interface implemented by LinkedList?

* `Serializable`: Allows the object to be converted into a byte stream. 
* `Cloneable`: allows the `LinkedList` to be copied field-by-field
* `Iterable`: specifically inherits `iterator` from `Deque`, lets the `LinkedList` be traversed head to tail. 
* `Collection`: inherits stream() and parallelStream() methods, which converts the `LinkedList` into a Stream; also inherits removeIf(), which can remove all instances of an input Object in the `LinkedList`
* `Deque`: For the Iterator
* `List`: Control over where in list each element is inserted
* `Queue`: Holding elements prior to processing.  

### What is the purpose of each superclass of HashMap?

* AbstractMap: copy and paste: "This class provides a skeletal implementation of the Map interface, to minimize the effort required to implement this interface."

* Object: copy paste: "Class Object is the root of the class hierarchy. Every class has Object as a superclass. All objects, including arrays, implement the methods of this class."



### How many different implementations are there for a Set?

- HashSet
- TreeSet
- LinkedHashSet


### What methods are common to all collections?
- add(E e)
- addAll(Collection<? extends E> c)
- clear()
- contains(Object o)
- containsAll(Collection<?> c)
- equals(Object o)
- hashCode()
- isEmpty()
- iterator()
- parallelStream()
- remove(Object o)
- removeAll(Collection<?> c)
- removeIf(Predicate<? super E> filter)
- retainAll(Collection<?> c)
- size()
- spliterator()
- stream()
- toArray()
- toArray(T[] a)

### What methods are common to all Queues?
Methods inherited from Collection and Iterable, along with:
- add(E e)
- element()
- offer (E e)
- peek()
- poll()
- remove()


### What is the purpose of the collection utility classes?
The utility classes consist of static methods that operate on or return collections of Objects. Essentially, at the highest level the utility class should contain methods that are applicable to all collections. 
