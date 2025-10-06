package Lab3;
import java.util.*;
class PyrotechnicsMap extends AbstractStorage<Pyrotechnics> {
    private List<Pyrotechnics> items = new ArrayList<>();
    private Map<String, Pyrotechnics> itemsMap = new HashMap<>();

    @Override
    public void addItem(Pyrotechnics item) {
        items.add(item);
        itemsMap.put(item.getId(), item);
    }

    @Override
    public void removeItem(String id) {
        Pyrotechnics item = itemsMap.remove(id);
        if (item != null) {
            items.remove(item);
        }
    }

    @Override
    public Pyrotechnics findItem(String id) {
        return itemsMap.get(id);
    }

    @Override
    public void displayAll() {
        Iterator<Pyrotechnics> iterator = items.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public Map<String, Pyrotechnics> getSortedByPrice() {
        Map<String, Pyrotechnics> sortedMap = new TreeMap<>(
                (id1, id2) -> Double.compare(itemsMap.get(id1).getPrice(), itemsMap.get(id2).getPrice())
        );
        sortedMap.putAll(itemsMap);
        return sortedMap;
    }
}