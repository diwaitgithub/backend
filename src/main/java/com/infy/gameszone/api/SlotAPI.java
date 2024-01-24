package com.infy.gameszone.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.gameszone.api.responses.GenericResponse;
import com.infy.gameszone.dto.SlotDTO;
import com.infy.gameszone.exception.GameszoneException;
import com.infy.gameszone.service.SlotService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@RestController
@Validated
@RequestMapping(value = "/game")
public class SlotAPI {

    @Autowired
    private SlotService slotService;

    @PutMapping(value = "/{gameId}/update/slot/{slotId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SlotDTO> updateSlot(
            @PathVariable(name = "gameId") @Pattern(regexp = "^[0-9]*$", message = "{game.gameId.invalid}") String gameId,
            @PathVariable(name = "slotId") @Pattern(regexp = "^[0-9]*$", message = "{slot.slotId.invalid}") String slotId,
            @RequestBody @Valid SlotDTO slot) throws GameszoneException {
        //
        SlotDTO updatedSlot = slotService.updateSlot(Integer.parseInt(gameId), Integer.parseInt(slotId), slot);
        //
        return new ResponseEntity<SlotDTO>(updatedSlot, HttpStatus.OK);
    }

    @PostMapping(value = "/{gameId}/add/slot")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SlotDTO> addSlot(
            @PathVariable(name = "gameId") @Pattern(regexp = "^[0-9]*$", message = "{game.gameId.invalid}") String gameId,
            @RequestBody @Valid SlotDTO slot)
            throws GameszoneException {

        SlotDTO newSlot = slotService.addSlot(Integer.parseInt(gameId), slot);

        return new ResponseEntity<SlotDTO>(newSlot, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/slot/{slotId}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<GenericResponse> deleteSlot(
            @PathVariable(name = "slotId") @Pattern(regexp = "^[0-9]*$", message = "{slot.slotId.invalid}") String slotId)
            throws GameszoneException {

        slotService.deleteSlot(Integer.parseInt(slotId));

        GenericResponse genericResponse = new GenericResponse("Successfully Deleted");

        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/{gameId}/slots/search")
    public ResponseEntity<Page<SlotDTO>> searchGameSlots(
            @PathVariable(name = "gameId") @Pattern(regexp = "^[0-9]*$", message = "{game.gameId.invalid}") String gameId,
            @RequestParam(name = "name", defaultValue = "") String nameQuery,
            @RequestParam(name = "location", defaultValue = "") String locationQuery,
            @RequestParam(name = "pageNo", defaultValue = "0") @Pattern(regexp = "^[0-9]*$", message = "{page.pageno.invalid}") String pageNo,
            @RequestParam(name = "limit", defaultValue = "10") @Pattern(regexp = "^[0-9]*$", message = "{page.limit.invalid}") @Min(value = 1, message = "{page.limit.min}") String limit,
            @RequestParam(name = "sort", defaultValue = "slotId.asc") @Pattern(regexp = "^(?:slotId|slotName|startTime|endTime|location)\\.(?:asc|desc)$", message = "{page.sort.invalid}") String sort)
            throws GameszoneException {

        Page<SlotDTO> slotsPage = slotService.searchSlots(
                Integer.parseInt(gameId),
                nameQuery,
                locationQuery,
                Integer.parseInt(pageNo),
                Integer.parseInt(limit),
                sort);

        return new ResponseEntity<>(slotsPage, HttpStatus.OK);

    }
}
