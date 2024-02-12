class TwoWayLinkedList<E> implements MyList<E> {
  private Node<E> head, tail;
  private int size;

  public TwoWayLinkedList() {
  }

  public TwoWayLinkedList(E[] objects) {
    for (E e : objects)
      add(e);
  }

  public E getFirst() {
    if (size == 0) {
      return null;
    } else {
      return head.element;
    }
  }

  public E getLast() {
    if (size == 0) {
      return null;
    } else {
      return tail.element;
    }
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("[");

    Node<E> current = head;
    for (int i = 0; i < size; i++) {
      result.append(current.element);
      current = current.next;
      if (current != null) {
        result.append(", ");
      } else {
        result.append("]");
      }
    }

    return result.toString();
  }

  public void clear() {
    head = tail = null;
    size = 0;
  }

  public boolean contains(Object e) {
    Node<E> current = head;
    while (current != null) {
      if (e.equals(current.element)) {
        return true;
      }
      current = current.next;
    }
    return false;
  }

  public E get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    Node<E> current = head;
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    return current.element;
  }

  public int indexOf(Object e) {
    Node<E> current = head;
    for (int i = 0; i < size; i++) {
      if (e.equals(current.element)) {
        return i;
      }
      current = current.next;
    }
    return -1;
  }

  public int lastIndexOf(E e) {
    Node<E> current = tail;
    for (int i = size - 1; i >= 0; i--) {
      if (e.equals(current.element)) {
        return i;
      }
      current = current.previous;
    }
    return -1;
  }

  public E set(int index, E e) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    Node<E> current = head;
    for (int i = 0; i < index; i++) {
      current = current.next;
    }

    E oldValue = current.element;
    current.element = e;
    return oldValue;
  }

  public void add(int index, E e) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    if (index == 0) {
      addFirst(e);
    } else if (index == size) {
      addLast(e);
    } else {
      Node<E> newNode = new Node<>(e);
      Node<E> current = head;
      for (int i = 0; i < index - 1; i++) {
        current = current.next;
      }

      newNode.next = current.next;
      newNode.previous = current;
      current.next.previous = newNode;
      current.next = newNode;

      size++;
    }
  }

  public E remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    if (index == 0) {
      return removeFirst();
    } else if (index == size - 1) {
      return removeLast();
    } else {
      Node<E> current = head;
      for (int i = 0; i < index; i++) {
        current = current.next;
      }

      current.previous.next = current.next;
      current.next.previous = current.previous;

      size--;

      return current.element;
    }
  }

  public E removeFirst() {
    if (size == 0) {
      return null;
    }

    E removedElement = head.element;
    head = head.next;
    if (head != null) {
      head.previous = null;
    } else {
      tail = null;
    }

    size--;

    return removedElement;
  }

  public E removeLast() {
    if (size == 0) {
      return null;
    }

    E removedElement = tail.element;
    tail = tail.previous;
    if (tail != null) {
      tail.next = null;
    } else {
      head = null;
    }

    size--;

    return removedElement;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean add(E e) {
    addLast(e);
    return true;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public boolean remove(Object e) {
    int index = indexOf(e);
    if (index >= 0) {
      remove(index);
      return true;
    }
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    for (Object element : c) {
      if (!contains(element)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    for (E element : c) {
      add(element);
    }
    return true;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    boolean modified = false;
    for (Object element : c) {
      modified |= remove(element);
    }
    return modified;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    boolean modified = false;
    Iterator<E> iterator = iterator();
    while (iterator.hasNext()) {
      if (!c.contains(iterator.next())) {
        iterator.remove();
        modified = true;
      }
    }
    return modified;
  }

  @Override
  public Object[] toArray() {
    Object[] array = new Object[size];
    Node<E> current = head;
    for (int i = 0; i < size; i++) {
      array[i] = current.element;
      current = current.next;
    }
    return array;
  }

  @Override
  public <T> T[] toArray(T[] array) {
      if (array.length < size) {
          array = Arrays.copyOf(array, size);
      }

      int i = 0;
      Object[] result = array;
      for (Node<E> current = head; current != null; current = current.next) {
          result[i++] = current.element;
      }

      if (array.length > size) {
          array[size] = null;
      }

      return array;
  }

@Override
  public Iterator<E> iterator() {
      return new LinkedListIterator
