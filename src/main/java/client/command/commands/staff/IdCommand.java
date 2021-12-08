package client.command.commands.staff;

import client.MapleCharacter;
import client.MapleClient;
import client.command.Command;
import tools.exceptions.IdTypeNotSupportedException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class IdCommand extends Command {
    {
        setDescription("");
    }

    private final Map<String, String> handbookDirectory = new HashMap<>();
    private final Map<String, HashMap<String, String>> itemMap = new HashMap<>();

    public IdCommand() {
        handbookDirectory.put("map", "handbook/Map.txt");
        handbookDirectory.put("etc", "handbook/Etc.txt");
        handbookDirectory.put("npc", "handbook/NPC.txt");
        handbookDirectory.put("use", "handbook/Use.txt");
        handbookDirectory.put("weapon", "handbook/Equip/Weapon.txt"); // TODO add more into this
    }

    @Override
    public void execute(MapleClient client, final String[] params) {
        final MapleCharacter player = client.getPlayer();
        if (params.length < 2) {
            player.yellowMessage("Syntax: !id <type> <query>");
            return;
        }
        final String queryItem = joinStringArr(Arrays.copyOfRange(params, 1, params.length), " ");
        player.yellowMessage("Querying for entry... May take some time... Please try to refine your search");
        Runnable queryRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    populateIdMap(params[0].toLowerCase());

                    Map<String, String> resultList = fetchResults(itemMap.get(params[0]), queryItem);

                    if (resultList.size() > 0) {
                        int count = 0;
                        for (Map.Entry<String, String> entry: resultList.entrySet()) {
                            player.yellowMessage(String.format("Id for %s is: %s", entry.getKey(), entry.getValue()));
                            if (++count > 100) {
                                break;
                            }
                        }
                        player.yellowMessage(String.format("Results found: %d | Returned: %d/100 | Refine search query to improve time.", resultList.size(), count - 1));
                    } else {
                        player.yellowMessage(String.format("Id not found for item: %s, of type: %s", queryItem, params[0]));
                    }
                } catch (IdTypeNotSupportedException e) {
                    player.yellowMessage("Your query type is not supported");
                } catch (IOException e) {
                    player.yellowMessage("Error reading file, please contact your administrator");
                }
            }
        };
        Thread thread = new Thread(queryRunnable);
        thread.start();
    }

    private void populateIdMap(String type) throws IdTypeNotSupportedException, IOException {
        if (!handbookDirectory.containsKey(type)) {
            throw new IdTypeNotSupportedException();
        }
        itemMap.put(type, new HashMap<String, String>());
        BufferedReader reader = new BufferedReader(new FileReader(handbookDirectory.get(type)));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(" - ", 2);
            if (row.length == 2) {
                itemMap.get(type).put(row[1].toLowerCase(), row[0]);
            }
        }
    }

    private String joinStringArr(String[] arr, String separator) {
        if (null == arr || 0 == arr.length) return "";
        StringBuilder sb = new StringBuilder(256);
        sb.append(arr[0]);
        for (int i = 1; i < arr.length; i++) sb.append(separator).append(arr[i]);
        return sb.toString();
    }

    private Map<String, String> fetchResults(Map<String, String> queryMap, String queryItem) {
        Map<String, String> results = new HashMap<>();
        for (String item: queryMap.keySet()) {
            if (item.indexOf(queryItem) != -1) {
                results.put(item, queryMap.get(item));
            }
        }
        return results;
    }
}
