package Lab3;
import java.util.*;
class PyrotechnicsList extends AbstractStorage<Pyrotechnics> {
    private List<Pyrotechnics> items = new ArrayList<>();

    @Override
    public void addItem(Pyrotechnics item) {
        items.add(item);
    }

    @Override
    public void removeItem(String id) {
        items.removeIf(item -> item.getId().equals(id));
    }

    @Override
    public Pyrotechnics findItem(String id) {
        for (Pyrotechnics item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void displayAll() {
        Iterator<Pyrotechnics> iterator = items.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public List<Pyrotechnics> getItems() {
        return items;
    }
}